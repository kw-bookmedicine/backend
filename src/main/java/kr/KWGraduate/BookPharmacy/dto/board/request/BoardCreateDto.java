package kr.KWGraduate.BookPharmacy.dto.board.request;

import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.enums.Keyword;
import kr.KWGraduate.BookPharmacy.enums.Status;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardCreateDto {
    private String title;
    private String description;
    private Keyword keyword;

    @Builder
    public BoardCreateDto(String title, String description, Keyword keyword){
        this.title = title;
        this.description = description;
        this.keyword = keyword;
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
