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
    private String koreanKeyword;
    private List<AnswerCreateDto> answers;

    @Builder
    public BoardCreateDto(String title, String description, String koreanKeyword, List<AnswerCreateDto> answerCreateDtos){
        this.title = title;
        this.description = description;
        this.koreanKeyword = koreanKeyword;
        this.answers = answerCreateDtos;
    }

    public Board toEntity(Client client,Keyword keyword){
        return Board.builder()
                .description(description)
                .title(title)
                .keyword(keyword)
                .client(client)
                .build();
    }
}
