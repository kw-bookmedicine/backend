package kr.KWGraduate.BookPharmacy.domain.client.dto.request;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientUpdateDto {
    String occupation;
    String description;
}
