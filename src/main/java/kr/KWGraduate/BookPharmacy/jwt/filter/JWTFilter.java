package kr.KWGraduate.BookPharmacy.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.entity.redis.RefreshToken;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
import kr.KWGraduate.BookPharmacy.service.ClientDetailsService;
import kr.KWGraduate.BookPharmacy.service.redis.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    //나중에 Detail에 dto로 변경해도 될듯함
    private final ClientDetailsService clientDetailsService;

    private final RefreshTokenService refreshTokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = "";
        String tokenInfo = "";
        try{
            //authorization = jwtUtil.resolveCookie(request,"Authorization");

            System.out.println("authorization now");
            //tokenInfo = authorization.split(",")[1];

            tokenInfo = jwtUtil.resolveCookie(request,"Authorization");

            if(!jwtUtil.validateToken(tokenInfo)){
                System.out.println("not valid");
                filterChain.doFilter(request, response);
                return;
            }

            Authentication authToken = jwtUtil.getAuthentication(tokenInfo);
            //세션에 사용자 등록
            SecurityContextHolder.getContext().setAuthentication(authToken);


        }catch (ExpiredJwtException e){
            String refreshTokenInfo = authorization.split(" ")[2];

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

            RefreshToken refreshToken = refreshTokenService.findByUsername(username);

            if(refreshToken.getAccessToken().equals(tokenInfo)){
                //재발급
                reissue(response, username, role);
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

    private void reissue(HttpServletResponse response, String username, String role) throws SignatureException {
        System.out.println("재발금");
        TokenDto jwt = jwtUtil.createJwt(username, role);

        refreshTokenService.save(jwt, username);

        response.addHeader("Authorization", jwt.getGrantType()+" "+jwt.getAccessToken() +" " +jwt.getRefreshToken());

        //jwtUtil쓰지말고 객체만들어서 반환 가능
        Authentication authToken = jwtUtil.getAuthentication(jwt.getAccessToken());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
