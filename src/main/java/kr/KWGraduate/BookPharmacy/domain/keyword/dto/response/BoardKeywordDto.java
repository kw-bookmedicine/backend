package kr.KWGraduate.BookPharmacy.domain.keyword.dto.response;

import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
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