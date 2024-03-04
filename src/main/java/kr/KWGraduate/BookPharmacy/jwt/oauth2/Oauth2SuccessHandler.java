package kr.KWGraduate.BookPharmacy.jwt.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.dto.oauth2.CustomOauth2Client;
import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.jwt.JWTUtil;
import kr.KWGraduate.BookPharmacy.service.redis.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static kr.KWGraduate.BookPharmacy.config.Domain.*;
import static kr.KWGraduate.BookPharmacy.jwt.CookieType.Authorization;
import static kr.KWGraduate.BookPharmacy.jwt.CookieType.Refresh;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        CustomOauth2Client oauth2Client = (CustomOauth2Client) authentication.getPrincipal();

        System.out.println(oauth2Client);
        String username = oauth2Client.getUsername();

        Collection<? extends GrantedAuthority> authorities = oauth2Client.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        TokenDto token = jwtUtil.createJwt(username, role,"true");

        refreshTokenService.save(token,username);
        response.addHeader(HttpHeaders.SET_COOKIE,Authorization.createCookie(token.getAccessToken()));
        response.addHeader(HttpHeaders.SET_COOKIE, Refresh.createCookie(token.getRefreshToken()));

        response.getWriter().write("success");
        response.sendRedirect(FrontServer.getPresentAddress());
    }


}
