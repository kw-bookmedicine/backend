package kr.KWGraduate.BookPharmacy.dto.readExperience.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ReadExperienceUpdateRequestDto {

    private List<String> bookIsbnList;

    @Builder
    public ReadExperienceUpdateRequestDto(List<String> bookIsbnList){
        this.bookIsbnList = bookIsbnList;
    }
}
