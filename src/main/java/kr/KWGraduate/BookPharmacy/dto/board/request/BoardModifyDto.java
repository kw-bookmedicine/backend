package kr.KWGraduate.BookPharmacy.dto.board.request;

import kr.KWGraduate.BookPharmacy.dto.answer.request.AnswerUpdateDto;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardModifyDto {
    private String title;
    private String description;
    private Keyword keyword;
    private AnswerUpdateDto answers;
    @Builder
    public BoardModifyDto(String title, String description, Keyword keyword,AnswerUpdateDto answerUpdateDto){
        this.title = title;
        this.description = description;
        this.keyword = keyword;
        this.answers = answerUpdateDto;
    }
}
