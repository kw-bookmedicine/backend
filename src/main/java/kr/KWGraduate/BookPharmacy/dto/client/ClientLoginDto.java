package kr.KWGraduate.BookPharmacy.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientLoginDto {

    @NotNull(message = "id가 null이면 안됨")
    @NotBlank(message = "id가 빈칸이면 안됨")
    private String username;
    @NotNull(message = "password가 null이면 안됨")
    @NotBlank(message = "password가 빈칸이면 안됨")
    private String password;

    @Builder
    public ClientLoginDto(String username,String password){
        this.username = username;
        this.password = password;
    }
}
