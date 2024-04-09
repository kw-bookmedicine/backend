package kr.KWGraduate.BookPharmacy.dto.prescription.response;

import kr.KWGraduate.BookPharmacy.entity.Prescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PrescriptionBoardPageDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private String nickname;
    private String book_title;
    private String author; // 저자명
    private String publishingHouse; // 출판사명
    private String publishYear; // 발행년도
    private String imageUrl; // 이미지 url

    @Builder
    public PrescriptionBoardPageDto(Prescription prescription){
        this.id = prescription.getId();
        this.title = prescription.getTitle();
        this.description = prescription.getDescription();
        this.createdDate = prescription.getCreatedDate();
        this.nickname = prescription.getClient().getNickname();
        this.book_title = prescription.getBook().getTitle();
        this.author = prescription.getBook().getAuthor();
        this.publishYear = prescription.getBook().getPublishYear();
        this.imageUrl = prescription.getBook().getImageUrl();
    }
}
