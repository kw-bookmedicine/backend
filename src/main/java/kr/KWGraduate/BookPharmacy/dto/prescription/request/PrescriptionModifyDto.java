package kr.KWGraduate.BookPharmacy.dto.prescription.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriptionModifyDto {
    private String title;
    private String description;
    private String isbn;

    @Builder
    public PrescriptionModifyDto(String title, String description, String isbn){
        this.title = title;
        this.description = description;
        this.isbn = isbn;
    }

}
