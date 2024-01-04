package kr.KWGraduate.BookPharmacy.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class BookSearchDto {
    private String title;
    private String author; // 저자명
    private String publicYear; // 발행년도

    @Builder
    public BookSearchDto(String title, String author, String publicYear) {
        this.title = title;
        this.author = author;
        this.publicYear = publicYear;
    }
}
