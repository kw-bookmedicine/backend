package kr.KWGraduate.BookPharmacy.jwt;

import jakarta.servlet.http.Cookie;

public enum CookieType {

    Authorization("Authorization"),Refresh("Refresh");

    private String key;

    CookieType(String key){
        this.key = key;
    }

    public Cookie createCookie(String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 5);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
