package kr.KWGraduate.BookPharmacy.global.security.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.global.common.error.ErrorCode;
import kr.KWGraduate.BookPharmacy.global.security.auth.dto.ClientDetails;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.TokenDto;
import kr.KWGraduate.BookPharmacy.global.common.error.BusinessException;
import kr.KWGraduate.BookPharmacy.global.security.common.util.JWTUtil;
import kr.KWGraduate.BookPharmacy.global.infra.redis.RefreshTokenService;
import org.springframework.http.HttpHeaders;
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

import static kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType.Authorization;
import static kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType.Refresh;

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
            throw new BusinessException("convert fail", ErrorCode.INTERNAL_SERVER_ERROR);
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
        response.addHeader(HttpHeaders.SET_COOKIE,Authorization.createCookie(token.getAccessToken()));
        response.addHeader(HttpHeaders.SET_COOKIE, Refresh.createCookie(token.getRefreshToken()));
        response.getWriter().write("success");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
        response.getWriter().write("fail");
    }
}
