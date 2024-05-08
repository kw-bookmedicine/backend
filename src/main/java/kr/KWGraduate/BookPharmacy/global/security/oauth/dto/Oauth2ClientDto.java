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

    @Builder
    public Oauth2ClientDto(String username, String name, String role) {
        this.username = username;
        this.name = name;
        this.role=role;
    }
}
