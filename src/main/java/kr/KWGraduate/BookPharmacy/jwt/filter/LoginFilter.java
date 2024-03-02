package kr.KWGraduate.BookPharmacy.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.dto.client.ClientDetails;
import kr.KWGraduate.BookPharmacy.dto.client.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.exception.status.AllException;
import kr.KWGraduate.BookPharmacy.jwt.CookieType;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
import kr.KWGraduate.BookPharmacy.service.redis.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final String CONTENT_TYPE = "application/json";
    private final ObjectMapper objectMapper;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper,JWTUtil jwtUtil,RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getContentType().equals(CONTENT_TYPE) || request.getContentType() == null){
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
            //예외처리에 대한 메서드 추가
        }

        ClientLoginDto loginDto = extractLoginDto(request);

        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        //Authentication을 구현한 객체임
        //Authentication은 접근하는 주체의 정보와 권한을 담는 인터페이스

        return authenticationManager.authenticate(authToken);
    }

    private ClientLoginDto extractLoginDto(HttpServletRequest request)  {
        try {
            return objectMapper.readValue(StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8), ClientLoginDto.class);
        } catch (IOException e) {
            throw new AllException("convert fail") {
                @Override
                public String getErrorMessage() {
                    return super.getErrorMessage();
                }
            };
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        ClientDetails clientDetails = (ClientDetails) authResult.getPrincipal();
        //provider의 결과로 여기에 저장됨

        String username = clientDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        TokenDto token = jwtUtil.createJwt(username, role,"false");

        refreshTokenService.save(token,username);

        response.addCookie(CookieType.Authorization.createCookie(token.getAccessToken()));
        response.addCookie(CookieType.Refresh.createCookie(token.getRefreshToken()));
        response.getWriter().write("success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        response.getWriter().write("fail");
    }
}
