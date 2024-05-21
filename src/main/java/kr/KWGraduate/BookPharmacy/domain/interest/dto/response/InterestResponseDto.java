package kr.KWGraduate.BookPharmacy.domain.interest.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InterestResponseDto {

    // 파이썬 서버에서 카테고리를 id로 받는다면 Long타입, 이름으로 받는다면 String타입이 될 것입니다.
    private List<Long> CategoryList;
}
