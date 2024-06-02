package kr.KWGraduate.BookPharmacy.global.security.common.util;

import io.jsonwebtoken.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.CustomOauth2Client;
import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.Oauth2ClientDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.TokenDto;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshToken;
import kr.KWGraduate.BookPharmacy.global.security.auth.service.ClientDetailsService;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.time.OffsetDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    private final SecretKey secretKey;
    private final long ACCESS_TOKEN_EXPIRATION_TIME_SECOND;
    private final long REFRESH_TOKEN_EXPIRATION_TIME_SECOND;

    private ClientDetailsService clientDetailsService;

    private final RefreshTokenService refreshTokenService;
    public JWTUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpiration,
            ClientDetailsService clientDetailsService,
            RefreshTokenService refreshTokenService) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        //암호화 방식은 Jwts.SIG.HS256
        ACCESS_TOKEN_EXPIRATION_TIME_SECOND = accessTokenExpiration;
        REFRESH_TOKEN_EXPIRATION_TIME_SECOND = refreshTokenExpiration;
        this.clientDetailsService = clientDetailsService;
        this.refreshTokenService = refreshTokenService;
    }
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }
    public String isOauth(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("isOauth",String.class);
    }
//    public Boolean isExpired(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
//    }

    public Boolean validateToken(String token){
        String username = getUsername(token);
        RefreshToken refreshToken = refreshTokenService.findByUsername(username);

        if(refreshToken.isLogout()){
            throw new JwtException("token is stilled");
        }

        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return true;
    }

    public TokenDto createJwt(String username, String role, String isOauth){

        String accessToken =  Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("isOauth", isOauth)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(OffsetDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION_TIME_SECOND).toInstant()))
                .signWith(secretKey)
                .compact();
        String refreshToken = Jwts.builder()
                .subject(username)
                .claim("isOauth", isOauth)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(OffsetDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_TIME_SECOND).toInstant()))
                .signWith(secretKey)
                .compact();
        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    public Authentication getAuthentication(String accessToken) throws SignatureException {
        //Claims claims = parseClaims(accessToken);

        if(isOauth(accessToken).equals("true")){
            Oauth2ClientDto oauth2ClientDto = Oauth2ClientDto.builder()
                    .username(getUsername(accessToken))
                    .role(getRole(accessToken))
                    .build();

            CustomOauth2Client customOauth2Client = new CustomOauth2Client(oauth2ClientDto);
            return new UsernamePasswordAuthenticationToken(customOauth2Client,null,customOauth2Client.getAuthorities());
        }else{

            String principal = getUsername(accessToken);
            UserDetails userDetails = clientDetailsService.loadUserByUsername(principal);

            System.out.println(userDetails.getUsername());
            for(var s : userDetails.getAuthorities()){
                System.out.println(s.getAuthority());
            }

            return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
        }

    }


    public String resolveCookie(HttpServletRequest request,String key){

        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            throw new AccessDeniedException("there is no role");
        }

        for(Cookie cookie : cookies){
            if (cookie.getName().equals(key)) {
                authorization = cookie.getValue();
            }
        }
        if (authorization == null) {
            throw new AccessDeniedException("there is no role");
        }

        return authorization;
    }

}
