package kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OneLineCreateDto {
    private String title;
    private String description;
    private String bookIsbn;
    private Keyword keyword;

    @Builder
    public OneLineCreateDto(String title, String description, String bookIsbn, Keyword keyword) {
        this.title = title;
        this.description = description;
        this.bookIsbn = bookIsbn;
        this.keyword = keyword;
    }

    public OneLinePrescription toEntity(Book book) {
        OneLinePrescription oneLinePrescription = OneLinePrescription.builder()
                .title(title)
                .description(description)
                .keyword(keyword)
                .book(book)
                .build();

        return oneLinePrescription;
    }
}
