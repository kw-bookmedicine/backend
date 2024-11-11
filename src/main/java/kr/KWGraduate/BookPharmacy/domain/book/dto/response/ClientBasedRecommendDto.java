package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.InterestRecommend;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientBasedRecommendDto {
    private Long id;
    private Long bookId;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public ClientBasedRecommendDto(ClientRecommend clientRecommend){
        Book book = clientRecommend.getBook();
        this.id = book.getId();
        this.bookId = book.getId();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this. author = book.getAuthor();
    }

    public ClientBasedRecommendDto(InterestRecommend interestRecommend){
        Book book = interestRecommend.getBook();
        this.id = book.getId();
        this.bookId = book.getId();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this. author = book.getAuthor();
    }
}
