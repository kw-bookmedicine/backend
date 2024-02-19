package kr.KWGraduate.BookPharmacy.jwt;

import io.jsonwebtoken.*;

import jakarta.servlet.http.HttpServletRequest;
import kr.KWGraduate.BookPharmacy.dto.client.ClientDetails;
import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.exception.status.AllException;
import kr.KWGraduate.BookPharmacy.service.ClientDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JWTUtil {
    private SecretKey secretKey;
    private final Long ACESS_TOKEN_EXPIRATION_TIME_SECOND = 60L; // 5분
    private final Long REFRESH_TOKEN_EXPIRATION_TIME_SECOND = 300L; // 5분

    private ClientDetailsService clientDetailsService;

    public JWTUtil( @Value("${spring.jwt.secret}") String secret, ClientDetailsService clientDetailsService) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
        //암호화 방식은 Jwts.SIG.HS256
        this.clientDetailsService = clientDetailsService;
    }
//    public String getUsername(String token){
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username",String.class);
//    }
//    public String getRole(String token){
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",String.class);
//    }
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
                .expiration(Date.from(OffsetDateTime.now().plusSeconds(ACESS_TOKEN_EXPIRATION_TIME_SECOND).toInstant()))
                .signWith(secretKey)
                .compact();
        String refreshToken = Jwts.builder()
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
