package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import lombok.Data;

@Data
public class ReadExperienceTop10Dto {

    private Long bookId;
    private Long count;

    public ReadExperienceTop10Dto(Long bookId, Long count) {
        this.bookId = bookId;
        this.count = count;
    }
}
