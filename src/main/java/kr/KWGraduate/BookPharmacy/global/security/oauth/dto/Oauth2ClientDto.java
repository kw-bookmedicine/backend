package kr.KWGraduate.BookPharmacy.global.security.oauth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Oauth2ClientDto {
    private String username;
    private String name;
    private String role;
    private String email;
    private Boolean isExist;

    @Builder
    public Oauth2ClientDto(String username, String name, String role, String email, Boolean isExist) {
        this.username = username;
        this.name = name;
        this.role=role;
        this.email = email;
        this.isExist = isExist;
    }
}
