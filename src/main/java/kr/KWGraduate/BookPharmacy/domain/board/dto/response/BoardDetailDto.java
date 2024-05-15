package kr.KWGraduate.BookPharmacy.domain.board.dto.response;

import kr.KWGraduate.BookPharmacy.domain.answer.dto.response.AnswerBoardPageDto;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class BoardDetailDto {
    private Long boardId;
    private String nickname;
    private LocalDate createdDate;
    private String title;
    private String description;
    private List<AnswerBoardPageDto> answers;

    @Builder
    public BoardDetailDto(Board board){
        this.boardId = board.getId();
        this.nickname = board.getClient().getNickname();
        this.createdDate = board.getCreatedDate().toLocalDate();
        this.title = board.getTitle();
        this.description = board.getDescription();
    }

    public void setAnswerBoardPageDto(List<AnswerBoardPageDto> answerBoardPageDto){
        this.answers = answerBoardPageDto;
    }
}
