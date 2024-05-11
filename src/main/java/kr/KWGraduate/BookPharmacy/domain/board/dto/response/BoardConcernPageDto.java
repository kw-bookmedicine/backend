package kr.KWGraduate.BookPharmacy.domain.board.dto.response;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardConcernPageDto {
    private Long boardId;
    private String nickname;
    private LocalDateTime createdDate;
    private String title;

    @Builder
    public BoardConcernPageDto(Board board){
        boardId = board.getId();
        nickname = board.getClient().getNickname();
        createdDate = board.getCreatedDate();
        title = board.getTitle();
    }

}