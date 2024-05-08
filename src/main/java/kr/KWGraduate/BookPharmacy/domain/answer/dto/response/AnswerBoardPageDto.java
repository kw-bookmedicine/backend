package kr.KWGraduate.BookPharmacy.domain.answer.dto.response;

import kr.KWGraduate.BookPharmacy.domain.answer.domain.Answer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerBoardPageDto {
    private Long id;
    private String question;
    private String answer;

    public AnswerBoardPageDto(Answer answer){
        this.id = answer.getId();
        this.question = answer.getQuestion();
        this.answer = answer.getAnswer();
    }
}
