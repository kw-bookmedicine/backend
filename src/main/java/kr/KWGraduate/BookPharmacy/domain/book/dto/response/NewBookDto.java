package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.NewBook;
import lombok.Builder;
import lombok.Data;

@Data
public class NewBookDto {

    private Long bookId;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public NewBookDto(NewBook newBook) {
        Book book = newBook.getBook();

        this.bookId = book.getId();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
