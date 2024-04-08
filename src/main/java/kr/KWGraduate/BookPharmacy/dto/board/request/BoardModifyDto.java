package kr.KWGraduate.BookPharmacy.dto.board.request;

import kr.KWGraduate.BookPharmacy.enums.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardModifyDto {
    String title;
    String description;
    Keyword keyword;

    @Builder
    public BoardModifyDto(String title, String description, Keyword keyword){
        this.title = title;
        this.description = description;
        this.keyword = keyword;
    }
}
