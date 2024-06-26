package kr.KWGraduate.BookPharmacy.global.config;

import lombok.Getter;

@Getter
public enum Domain {
    LocalHttp("http://localhost:3000",".localhost"), LocalHttps("https://localhost:3000",".localhost"), FrontServer("https://www.bookpharmacy.store",".bookpharmacy.store");

    private final String address;
    private final String domain;

    private Domain(String address, String domain){
        this.address = address;
        this.domain = domain;
    }
    public String getPresentAddress(){
        return FrontServer.address;
    }
    public String getPresentDomain(){
        return FrontServer.domain;
    }
}
