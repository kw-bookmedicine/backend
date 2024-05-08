package kr.KWGraduate.BookPharmacy.domain.board.dto.request;

import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerCreateDto;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
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
