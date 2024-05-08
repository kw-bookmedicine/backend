package kr.KWGraduate.BookPharmacy.domain.answer.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AnswerUpdateDto {
    Long boardId;
    List<AnswerUpdateIndividualDto> answers;

    public AnswerUpdateDto(Long boardId, List<AnswerUpdateIndividualDto> answers){
        this.boardId = boardId;
        this.answers = answers;
    }
}
