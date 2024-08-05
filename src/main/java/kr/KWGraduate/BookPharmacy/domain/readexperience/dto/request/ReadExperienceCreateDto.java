package kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReadExperienceCreateDto {

    private Long bookId;

    @Builder
    public ReadExperienceCreateDto(Long bookId){
        this.bookId = bookId;
    }
}
