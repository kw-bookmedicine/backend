package kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReadExperienceUpdateRequestDto {

    private List<Long> bookIdList;

    @Builder
    public ReadExperienceUpdateRequestDto(List<Long> bookIdList){
        this.bookIdList = bookIdList;
    }
}
