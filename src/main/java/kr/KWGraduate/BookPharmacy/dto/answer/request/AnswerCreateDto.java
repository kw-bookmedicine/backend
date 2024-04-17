package kr.KWGraduate.BookPharmacy.dto.answer.request;

import kr.KWGraduate.BookPharmacy.entity.Answer;
import kr.KWGraduate.BookPharmacy.entity.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerCreateDto {
    private String answer;
    private String question;

    public Answer toEntity(Board board){
        return Answer.builder()
                .answer(answer)
                .question(question)
                .board(board)
                .build();
    }

    public AnswerCreateDto(String question, String answer){
        this.answer = answer;
        this.question = question;
    }
}
