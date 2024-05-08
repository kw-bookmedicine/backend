package kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadExperienceCreateDto {

    private String bookIsbn;

    @Builder
    public ReadExperienceCreateDto(String bookIsbn){
        this.bookIsbn = bookIsbn;
    }
}
