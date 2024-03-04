package kr.KWGraduate.BookPharmacy.service;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.entity.redis.RefreshToken;
import kr.KWGraduate.BookPharmacy.jwt.CookieType;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
import kr.KWGraduate.BookPharmacy.service.redis.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final RefreshTokenService refreshTokenService;
    private final JWTUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

            String accessToken = jwtUtil.resolveCookie(request, CookieType.Authorization.name());
            String username = jwtUtil.getUsername(accessToken);

            RefreshToken refreshToken = refreshTokenService.findByUsername(username);
            refreshToken.setLogout();

            refreshTokenService.save(refreshToken);



    }
}
