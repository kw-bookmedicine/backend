package kr.KWGraduate.BookPharmacy.domain.book.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BookSearchRequestDto {
    private String searchWord;
    private List<String> keywordList;

    public BookSearchRequestDto(String searchWord, List<String> keywordList) {
        this.searchWord = searchWord;
        this.keywordList = keywordList;
    }
}
