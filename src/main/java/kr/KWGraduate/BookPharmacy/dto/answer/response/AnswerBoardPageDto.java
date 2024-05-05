package kr.KWGraduate.BookPharmacy.dto.answer.response;

import kr.KWGraduate.BookPharmacy.entity.Answer;
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
