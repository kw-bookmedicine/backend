package kr.KWGraduate.BookPharmacy.jwt;

import io.jsonwebtoken.*;

import jakarta.servlet.http.HttpServletRequest;
import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.service.ClientDetailsService;
import org.springframework.beans.factory.annotation.Value;
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

    public JWTUtil(
            @Value("${spring.jwt.secret}") String secret,
            @Value("${spring.jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${spring.jwt.refresh-token-expiration}") long refreshTokenExpiration,
            ClientDetailsService clientDetailsService) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        //암호화 방식은 Jwts.SIG.HS256
        ACCESS_TOKEN_EXPIRATION_TIME_SECOND = accessTokenExpiration;
        REFRESH_TOKEN_EXPIRATION_TIME_SECOND = refreshTokenExpiration;
        this.clientDetailsService = clientDetailsService;
    }
    public String getUsername(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public String getRole(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
    }
//    public Boolean isExpired(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
//    }

    public Boolean validateToken(String token){
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return true;
    }

    public TokenDto createJwt(String username, String role){

        String accessToken =  Jwts.builder()
                .subject(username)
                //.claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(OffsetDateTime.now().plusSeconds(ACCESS_TOKEN_EXPIRATION_TIME_SECOND).toInstant()))
                .signWith(secretKey)
                .compact();
        String refreshToken = Jwts.builder()
                .subject(username)
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
        Claims claims = parseClaims(accessToken);

        String principal = claims.getSubject();
        UserDetails userDetails = clientDetailsService.loadUserByUsername(principal);

        System.out.println(userDetails.getUsername());
        for(var s : userDetails.getAuthorities()){
            System.out.println(s.getAuthority());
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
    }

    private Claims parseClaims(String accessToken) throws SignatureException {
        try{
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        }catch(UnsupportedJwtException e){
            throw new SignatureException("parser가 동작 안함");
        }

    }

    public String resolveToken(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new JwtException("token is null or not start Bearer");
        }

        return authorization;
    }

}
