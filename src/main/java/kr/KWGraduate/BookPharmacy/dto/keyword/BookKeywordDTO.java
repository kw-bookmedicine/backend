package kr.KWGraduate.BookPharmacy.dto.keyword;

import kr.KWGraduate.BookPharmacy.entity.BookKeyword;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class BookKeywordDTO {
    String isbn;
    List<String> keywords = new ArrayList<>();

    @Builder
    public BookKeywordDTO(String isbn, List<KeywordItem> keywordItems){
        this.isbn = isbn;
        for(KeywordItem k : keywordItems){
            keywords.add(k.getName());
        }
    }
}
