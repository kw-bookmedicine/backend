package kr.KWGraduate.BookPharmacy.domain.readexperience.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.readexperience.domain.ReadExperience;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadExperienceResponseDto {
    private String bookTitle;
    private Long bookId;
    private String bookAuthor;

    @Builder
    public ReadExperienceResponseDto(String bookTitle, Long bookId, String bookAuthor){
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.bookAuthor = bookAuthor;
    }

    public ReadExperienceResponseDto(ReadExperience readExperience) {
        Book book = readExperience.getBook();
        this.bookTitle = book.getTitle();
        this.bookId = book.getId();
        this.bookAuthor = book.getAuthor();
    }
}
