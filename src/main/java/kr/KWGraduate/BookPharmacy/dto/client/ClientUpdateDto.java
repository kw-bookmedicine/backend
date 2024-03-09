package kr.KWGraduate.BookPharmacy.dto.client;

import kr.KWGraduate.BookPharmacy.entity.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientUpdateDto {
    String password;
    String nickname;
    Client.Occupation occupation;
}
