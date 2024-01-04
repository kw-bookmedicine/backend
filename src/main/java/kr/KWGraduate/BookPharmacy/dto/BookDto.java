package kr.KWGraduate.BookPharmacy.dto;

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

    @Builder
    public BookDto(String isbn, String title, String author, String publicYear, String content, String mediaFlagNumber) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicYear = publicYear;
        this.content = content;
        this.mediaFlagNumber = mediaFlagNumber;
    }
}
