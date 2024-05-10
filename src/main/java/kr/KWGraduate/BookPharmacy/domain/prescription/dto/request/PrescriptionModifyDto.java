package kr.KWGraduate.BookPharmacy.domain.prescription.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriptionModifyDto {
    private String title;
    private String description;
    private String bookIsbn;

    @Builder
    public PrescriptionModifyDto(String title, String description, String bookIsbn){
        this.title = title;
        this.description = description;
        this.bookIsbn = bookIsbn;
    }

}
