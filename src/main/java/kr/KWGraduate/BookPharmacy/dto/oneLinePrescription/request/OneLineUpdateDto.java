package kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.request;

import kr.KWGraduate.BookPharmacy.entity.Keyword;
import lombok.Builder;
import lombok.Data;

@Data
public class OneLineUpdateDto {
    private String title;
    private String description;
    private String bookIsbn;
    private String keyword;

    @Builder
    public OneLineUpdateDto(String title, String description, String bookIsbn, String keyword) {
        this.title = title;
        this.description = description;
        this.bookIsbn = bookIsbn;
        this.keyword = keyword;
    }
}
