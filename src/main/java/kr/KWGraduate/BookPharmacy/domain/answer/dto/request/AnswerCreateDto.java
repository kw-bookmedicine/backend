package kr.KWGraduate.BookPharmacy.domain.answer.dto.request;

import kr.KWGraduate.BookPharmacy.domain.answer.domain.Answer;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AnswerCreateDto {
    private String question;
    private String answer;

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
