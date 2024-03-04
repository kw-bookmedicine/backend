package kr.KWGraduate.BookPharmacy.config;

import lombok.Getter;

@Getter
public enum Domain {
    LocalHttp("http://localhost:3000"), LocalHttps("https://localhost:3000"), FrontServer("https://localhost:3000");

    private final String address;
    private final String domain = ".localhost";
    private final String serverDomain = "";

    private Domain(String address){
        this.address = address;
    }
    public String getPresentAddress(){
        return LocalHttps.getAddress();
    }
    public String getPresentDomain(){
        return domain;
    }
}
