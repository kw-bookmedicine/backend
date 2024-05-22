package kr.KWGraduate.BookPharmacy.domain.interest.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InterestRequestDto {

    private List<String> CategoryList;
}
