package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Book;
import lombok.Builder;
import lombok.Data;

@Data
public class BookSearchDto {
    private String title;
    private String author; // 저자명
    private String publicYear; // 발행년도
    private String isbn; // 책 고유번호 (책 조회할 때 pk로 쓰임)

    @Builder
    public BookSearchDto(String title, String author, String publicYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicYear = publicYear;
        this.isbn = isbn;
    }

    public BookSearchDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicYear = book.getPublicYear();
        this.isbn = book.getIsbn();
    }
}
