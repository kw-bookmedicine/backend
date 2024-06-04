package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.BoardRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardBasedRecommendDto {
    private Long id;
    private String isbn;
    private String imageUrl;
    private String title;
    private String author;
    private String[] keywords;
    private Boolean recommending;

    @Builder
    public BoardBasedRecommendDto(BoardRecommend boardRecommend){
        Book book = boardRecommend.getBook();

        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.keywords = boardRecommend.getKeywords().split(" ");
        this.recommending = false;
    }

    public BoardBasedRecommendDto(Boolean recommending){
        this.recommending = recommending;
    }
}
