package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.BestSellerBook;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import lombok.Builder;
import lombok.Data;

@Data
public class BestSellerBookDto {

    private String isbn;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public BestSellerBookDto(BestSellerBook bestSellerBook) {
        Book book = bestSellerBook.getBook();

        this.isbn = book.getIsbn();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
