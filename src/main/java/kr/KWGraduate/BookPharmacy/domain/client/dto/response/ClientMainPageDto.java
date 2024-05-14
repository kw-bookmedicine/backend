package kr.KWGraduate.BookPharmacy.domain.client.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClientMainPageDto {
    private String nickname;

    @Builder
    public ClientMainPageDto(String nickname) {
        this.nickname = nickname;
    }

}
