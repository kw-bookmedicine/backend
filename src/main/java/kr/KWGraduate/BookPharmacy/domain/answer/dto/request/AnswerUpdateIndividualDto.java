package kr.KWGraduate.BookPharmacy.domain.answer.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerUpdateIndividualDto {
    Long id;
    String answer;

    public AnswerUpdateIndividualDto(Long id, String answer){
        this.id= id;
        this.answer = answer;
    }
}
