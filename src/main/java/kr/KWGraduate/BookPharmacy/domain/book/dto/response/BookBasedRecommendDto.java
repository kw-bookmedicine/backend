package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BookRecommend;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookBasedRecommendDto {
    private Long id;
    private Long bookId;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public BookBasedRecommendDto(BookRecommend bookRecommend){
        Book book = bookRecommend.getRecommendingBook();
        this.id = book.getId();
        this.bookId = book.getId();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();
    }
}
