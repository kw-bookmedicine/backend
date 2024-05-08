package kr.KWGraduate.BookPharmacy.global.security.oauth.dto;

public interface Oauth2Response {
    //ex) naver, google
    String getProvider();
    //도메인에서 발급해주는 아이디
    String getProviderId();
    String getEmail();

    String getName();
}
