package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Book;
import lombok.Builder;
import lombok.Data;

@Data
public class BookDto {

    private String isbn;
    private String title;
    private String author; // 저자명
    private String publicYear; // 발행년도
    private String content; // 책내용
    private String mediaFlagNumber; // 미디어구분명
    private String bigCategoryName; // 대분류명
    private String middleCategoryName; // 중분류명

    public BookDto(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicYear = book.getPublicYear();
        this.content = book.getContent();
        this.mediaFlagNumber = book.getMediaFlagNumber();
        this.bigCategoryName = book.getBigCategory().getName();
        this.middleCategoryName = book.getMiddleCategory().getName();
    }

    @Builder
    public BookDto(String isbn, String title, String author, String publicYear, String content, String mediaFlagNumber, String bigCategoryName, String middleCategoryName) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicYear = publicYear;
        this.content = content;
        this.mediaFlagNumber = mediaFlagNumber;
        this.bigCategoryName = bigCategoryName;
        this.middleCategoryName = middleCategoryName;
    }
}
