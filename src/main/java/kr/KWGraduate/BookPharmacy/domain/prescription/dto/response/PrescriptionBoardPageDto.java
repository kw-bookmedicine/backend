package kr.KWGraduate.BookPharmacy.domain.prescription.dto.response;

import kr.KWGraduate.BookPharmacy.domain.prescription.domain.Prescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PrescriptionBoardPageDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdDate;
    private String nickname;
    private String bookTitle;
    private String author; // 저자명
    private String publishingHouse; // 출판사명
    private String publishYear; // 발행년도
    private String imageUrl; // 이미지 url
    private Long bookId;

    @Builder
    public PrescriptionBoardPageDto(Prescription prescription){
        this.id = prescription.getId();
        this.title = prescription.getTitle();
        this.description = prescription.getDescription();
        this.createdDate = prescription.getCreatedDate().toLocalDate();
        this.nickname = prescription.getClient().getNickname();
        this.bookTitle = prescription.getBook().getTitle();
        this.author = prescription.getBook().getAuthor();
        this.publishYear = prescription.getBook().getPublishYear();
        this.imageUrl = prescription.getBook().getImageUrl();
        this.bookId = prescription.getBook().getId();
    }
}
