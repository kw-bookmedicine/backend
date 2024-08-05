package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.BestSellerBook;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import lombok.Builder;
import lombok.Data;

@Data
public class BestSellerBookDto {

    private Long bookId;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public BestSellerBookDto(BestSellerBook bestSellerBook) {
        Book book = bestSellerBook.getBook();

        this.bookId = book.getId();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
