package kr.KWGraduate.BookPharmacy.dto.readExperience.request;

import lombok.Builder;
import lombok.Data;

@Data
public class ReadExperienceCreateDto {

    private String bookIsbn;

    @Builder
    public ReadExperienceCreateDto(String bookIsbn){
        this.bookIsbn = bookIsbn;
    }
}
