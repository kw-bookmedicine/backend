package kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.request;

import lombok.Builder;
import lombok.Data;

@Data
public class OneLineCreateDto {
    private String title;
    private String description;
    private String bookIsbn;
    private String keyword;

    @Builder
    public OneLineCreateDto(String title, String description, String bookIsbn, String keyword) {
        this.title = title;
        this.description = description;
        this.bookIsbn = bookIsbn;
        this.keyword = keyword;
    }
}
