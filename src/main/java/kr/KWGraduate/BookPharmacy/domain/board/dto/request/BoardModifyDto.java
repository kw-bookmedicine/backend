package kr.KWGraduate.BookPharmacy.domain.board.dto.request;

import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
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
