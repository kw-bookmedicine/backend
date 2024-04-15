package kr.KWGraduate.BookPharmacy.dto.board.response;

import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardMyPageDto {
    private Long boardId;
    private String title;
    private LocalDateTime createdDate;
    private Keyword keyword;
    private Long prescriptionCount;

    @Builder
    public BoardMyPageDto(Board board, Long count){
        boardId = board.getId();
        title = board.getTitle();
        createdDate = board.getCreatedDate();
        keyword = board.getKeyword();
        prescriptionCount = count;
    }

}
