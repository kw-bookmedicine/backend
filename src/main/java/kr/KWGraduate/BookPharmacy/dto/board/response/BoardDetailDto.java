package kr.KWGraduate.BookPharmacy.dto.board.response;

import kr.KWGraduate.BookPharmacy.entity.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDetailDto {
    private Long boardId;
    private String nickname;
    private LocalDateTime createdDate;
    private String title;
    private String description;

    @Builder
    public BoardDetailDto(Board board){
        this.boardId = board.getId();
        this.nickname = board.getClient().getNickname();
        this.createdDate = board.getCreatedDate();
        this.title = board.getTitle();
        this.description = board.getDescription();
    }
}
