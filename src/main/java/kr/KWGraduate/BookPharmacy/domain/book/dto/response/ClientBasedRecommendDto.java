package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientBasedRecommendDto {
    private Long id;
    private String isbn;
    private String imageUrl;
    private String title;
    private String author;

    @Builder
    public ClientBasedRecommendDto(Book book){
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this. author = book.getAuthor();
    }
}
