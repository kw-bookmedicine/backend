package kr.KWGraduate.BookPharmacy.dto.board.request;

import kr.KWGraduate.BookPharmacy.dto.answer.request.AnswerCreateDto;
import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BoardCreateDto {
    private String title;
    private String description;
    private Keyword keyword;
    private List<AnswerCreateDto> answers;

    @Builder
    public BoardCreateDto(String title, String description, Keyword keyword, List<AnswerCreateDto> answerCreateDtos){
        this.title = title;
        this.description = description;
        this.keyword = keyword;
        this.answers = answerCreateDtos;
    }

    public Board toEntity(Client client){
        return Board.builder()
                .description(description)
                .title(title)
                .keyword(keyword)
                .client(client)
                .build();
    }
}
