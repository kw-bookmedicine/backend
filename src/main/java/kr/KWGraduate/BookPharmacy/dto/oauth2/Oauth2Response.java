package kr.KWGraduate.BookPharmacy.dto.oauth2;

public interface Oauth2Response {
    //ex) naver, google
    String getProvider();
    //도메인에서 발급해주는 아이디
    String getProviderId();
    String getEmail();

    String getName();
}
