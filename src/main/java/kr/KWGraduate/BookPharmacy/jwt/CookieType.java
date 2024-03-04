package kr.KWGraduate.BookPharmacy.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.KWGraduate.BookPharmacy.config.Domain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

import static kr.KWGraduate.BookPharmacy.config.Domain.*;

public enum CookieType {

    Authorization("Authorization"),Refresh("Refresh");

    private String key;

    CookieType(String key){
        this.key = key;
    }

    public String createCookie(String token){
        return  ResponseCookie.from(key,token)
                .sameSite("Lax")
                .domain(FrontServer.getPresentDomain())
                .maxAge(60*5)
                .secure(true)
                .path("/")
                .httpOnly(true)
                .build()
                .toString();
    }
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response){

        if(request.getCookies().length <= 0){
            return;
        }

        String accessCookie = ResponseCookie.from(Authorization.key,"")
                .sameSite("Lax")
                .domain(FrontServer.getPresentDomain())
                .maxAge(0)
                .secure(true)
                .path("/")
                .httpOnly(true)
                .build()
                .toString();

        String refreshCookie = ResponseCookie.from(Refresh.key,"")
                .sameSite("Lax")
                .domain(FrontServer.getPresentDomain())
                .maxAge(0)
                .secure(true)
                .path("/")
                .httpOnly(true)
                .build()
                .toString();

        response.addHeader(HttpHeaders.SET_COOKIE,accessCookie);
        response.addHeader(HttpHeaders.SET_COOKIE,refreshCookie);
    }
}
