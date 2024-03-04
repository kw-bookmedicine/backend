package kr.KWGraduate.BookPharmacy.jwt;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;

public enum CookieType {

    Authorization("Authorization"),Refresh("Refresh");

    private String key;

    CookieType(String key){
        this.key = key;
    }

//    public Cookie createCookie(String value){
//        Cookie cookie = new Cookie(key, value);
//        cookie.setMaxAge(60 * 5);
//        cookie.setSecure(true);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
//
//        return cookie;
//    }
    public String createCookie(String token){
        return  ResponseCookie.from(key,token)
                .sameSite("Lax")
                .domain("https://localhost:3030")
                .maxAge(60*5)
                .secure(true)
                .path("/")
                .httpOnly(true)
                .build()
                .toString();
    }
}
