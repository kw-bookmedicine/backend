package kr.KWGraduate.BookPharmacy.dto.readExperience.response;

import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.ReadExperience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class ReadExperienceResponseDto {
    private String bookTitle;
    private String bookIsbn;
    private String bookAuthor;

    @Builder
    public ReadExperienceResponseDto(String bookTitle, String bookIsbn, String bookAuthor){
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
    }

    public ReadExperienceResponseDto(ReadExperience readExperience) {
        Book book = readExperience.getBook();
        this.bookTitle = book.getTitle();
        this.bookIsbn = book.getIsbn();
        this.bookAuthor = book.getAuthor();
    }
}
