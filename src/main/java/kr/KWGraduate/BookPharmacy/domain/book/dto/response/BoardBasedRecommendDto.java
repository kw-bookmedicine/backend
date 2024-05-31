package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.repository.BoardBasedRecommendRepositoryDto;
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

    @Builder
    public BoardBasedRecommendDto(BoardBasedRecommendRepositoryDto dto){
        Book book = dto.getBook();
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.imageUrl = book.getImageUrl();
        this.title = book.getTitle();
        this.author = book.getAuthor();

        this.keywords = dto.getKeywords().split(" ");
    }
}
