package kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request;

import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OneLineUpdateDto {
    private String title;
    private String description;
    private Long bookId;
    private Keyword keyword;

    @Builder
    public OneLineUpdateDto(String title, String description, Long bookId, Keyword keyword) {
        this.title = title;
        this.description = description;
        this.bookId = bookId;
        this.keyword = keyword;
    }
}
