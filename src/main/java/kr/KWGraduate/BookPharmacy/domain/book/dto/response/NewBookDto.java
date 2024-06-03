package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.NewBook;
import lombok.Builder;
import lombok.Data;

@Data
public class NewBookDto {

    private String isbn;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public NewBookDto(NewBook newBook) {
        Book book = newBook.getBook();

        this.isbn = book.getIsbn();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
