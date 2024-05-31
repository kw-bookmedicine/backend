package kr.KWGraduate.BookPharmacy.domain.book.dto.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardBasedRecommendRepositoryDto {
    private Book book;
    private String keywords;

    public BoardBasedRecommendRepositoryDto(Book book, String keywords){
        this.book = book;
        this.keywords = keywords;
    }
}
