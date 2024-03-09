package kr.KWGraduate.BookPharmacy.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientLoginDto {

    private String username;
    private String password;

    @Builder
    public ClientLoginDto(String username,String password){
        this.username = username;
        this.password = password;
    }
}
