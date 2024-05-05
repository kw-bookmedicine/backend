package kr.KWGraduate.BookPharmacy.dto.boardkeyword;

import kr.KWGraduate.BookPharmacy.entity.Keyword;
import lombok.Data;

@Data
public class BoardKeywordDto {
    private Keyword keyword;
    private String koreanKeyword;

    public BoardKeywordDto(Keyword keyword, String koreanKeyword) {
        this.keyword = keyword;
        this.koreanKeyword = koreanKeyword;
    }
}
