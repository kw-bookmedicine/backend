package kr.KWGraduate.BookPharmacy.global.security.oauth.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2.Oauth2SignUpService;
import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.CustomOauth2Client;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.TokenDto;
import kr.KWGraduate.BookPharmacy.global.security.common.util.JWTUtil;
import kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import static kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType.Authorization;
import static kr.KWGraduate.BookPharmacy.global.security.common.util.CookieType.Refresh;

@Component
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final Oauth2SignUpService oauth2SignUpService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        CustomOauth2Client oauth2Client = (CustomOauth2Client) authentication.getPrincipal();

        String email = oauth2Client.getEmail();
        String name = oauth2Client.getName();
        String username = oauth2Client.getUsername();

        Collection<? extends GrantedAuthority> authorities = oauth2Client.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        if(oauth2Client.isExist()){
            TokenDto token = jwtUtil.createJwt(username, role,"true");

            refreshTokenService.save(token,username);
            response.addHeader(HttpHeaders.SET_COOKIE,Authorization.createCookie(token.getAccessToken()));
            response.addHeader(HttpHeaders.SET_COOKIE, Refresh.createCookie(token.getRefreshToken()));

            response.sendRedirect("https://www.bookpharmacy.store/main");
//            response.sendRedirect("https://localhost:3000");
        }else{
            oauth2SignUpService.save(username,email,name);

            response.sendRedirect("https://www.bookpharmacy.store/signup/oauth?email="+email);
//            response.sendRedirect("https://localhost:3000?email="+email);
        }


    }


}
