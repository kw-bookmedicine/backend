package kr.KWGraduate.BookPharmacy.global.security.common.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshToken;
import kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType;
import kr.KWGraduate.BookPharmacy.global.security.common.util.JWTUtil;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

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
