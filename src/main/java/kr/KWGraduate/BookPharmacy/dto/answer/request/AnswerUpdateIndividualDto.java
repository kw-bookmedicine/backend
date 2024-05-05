package kr.KWGraduate.BookPharmacy.dto.answer.request;

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
