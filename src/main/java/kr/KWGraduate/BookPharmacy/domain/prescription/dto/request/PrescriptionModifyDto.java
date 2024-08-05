package kr.KWGraduate.BookPharmacy.domain.prescription.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriptionModifyDto {
    private String title;
    private String description;
    private Long bookId;

    @Builder
    public PrescriptionModifyDto(String title, String description, Long bookId){
        this.title = title;
        this.description = description;
        this.bookId = bookId;
    }

}
