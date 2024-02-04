package kr.KWGraduate.BookPharmacy.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.dto.client.ClientDetails;
import kr.KWGraduate.BookPharmacy.dto.client.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.exception.status.AllException;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
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
    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper,JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
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

        System.out.println(request.getContentType());

        System.out.println(username);
        System.out.println(password);
        System.out.println("here");

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

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

        String username = clientDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
