package kr.KWGraduate.BookPharmacy.global.security.common.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.TokenDto;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshToken;
import kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType;
import kr.KWGraduate.BookPharmacy.global.security.common.util.JWTUtil;
import kr.KWGraduate.BookPharmacy.global.security.auth.service.ClientDetailsService;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

import static kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType.Authorization;
import static kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType.Refresh;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    //나중에 Detail에 dto로 변경해도 될듯함
    private final ClientDetailsService clientDetailsService;

    private final RefreshTokenService refreshTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenInfo = "";
        try{

            tokenInfo = jwtUtil.resolveCookie(request, Authorization.name());

            if(!jwtUtil.validateToken(tokenInfo)){
                System.out.println("not valid");
                filterChain.doFilter(request, response);
                return;
            }

            Authentication authToken = jwtUtil.getAuthentication(tokenInfo);
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);


        }catch (ExpiredJwtException e){

            String refreshTokenInfo = jwtUtil.resolveCookie(request, CookieType.Refresh.name());
            checkingReissuing(request, response, refreshTokenInfo, tokenInfo);

        } catch (Exception e){
            request.setAttribute("exception",e);
        }
        finally {
            filterChain.doFilter(request, response);
        }
    }

    private void checkingReissuing(HttpServletRequest request, HttpServletResponse response, String refreshTokenInfo, String tokenInfo) {
        try{
            String username = jwtUtil.getUsername(refreshTokenInfo);
            String role = jwtUtil.getRole(refreshTokenInfo);
            String isOauth = jwtUtil.isOauth(refreshTokenInfo);

            RefreshToken refreshToken = refreshTokenService.findByUsername(username);

            if(refreshToken.isLogout()){
                throw new JwtException("token is stilled");
            }

            if(refreshToken.getAccessToken().equals(tokenInfo)){
                //재발급
                reissue(response, username, role, isOauth);
            }else{
                //없애고 재로그임
                System.out.println("재로그인");
                refreshTokenService.deleteToken(username);
                throw new JwtException("token is stilled");
            }
        }catch (Exception exception){
            System.out.println(exception.getMessage());
            request.setAttribute("exception",exception);
        }
    }

    private void reissue(HttpServletResponse response, String username, String role,String isOauth) throws SignatureException {
        System.out.println("재발금");
        TokenDto token = jwtUtil.createJwt(username, role, isOauth);

        refreshTokenService.save(token, username);


        response.addHeader(HttpHeaders.SET_COOKIE,Authorization.createCookie(token.getAccessToken()));
        response.addHeader(HttpHeaders.SET_COOKIE, Refresh.createCookie(token.getRefreshToken()));

        //jwtUtil쓰지말고 객체만들어서 반환 가능
        Authentication authToken = jwtUtil.getAuthentication(token.getAccessToken());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}
