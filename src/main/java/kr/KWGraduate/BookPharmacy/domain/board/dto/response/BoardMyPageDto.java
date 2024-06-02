package kr.KWGraduate.BookPharmacy.domain.board.dto.response;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardMyPageDto {
    private Long boardId;
    private String nickname;
    private String title;
    private LocalDate createdDate;
    private Keyword keyword;
    private Long prescriptionCount;

    @Builder
    public BoardMyPageDto(Board board, Long count){
        boardId = board.getId();
        nickname = board.getClient().getNickname();
        title = board.getTitle();
        createdDate = board.getCreatedDate().toLocalDate();
        keyword = board.getKeyword();
        prescriptionCount = count;
    }

}
