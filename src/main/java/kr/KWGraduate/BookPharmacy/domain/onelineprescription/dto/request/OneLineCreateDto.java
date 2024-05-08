package kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
