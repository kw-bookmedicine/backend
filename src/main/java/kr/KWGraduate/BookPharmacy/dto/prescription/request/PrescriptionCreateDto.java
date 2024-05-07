package kr.KWGraduate.BookPharmacy.dto.prescription.request;

import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Prescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriptionCreateDto {
    private String title;
    private String description;
    private String isbn;
    private Long boardId;

    @Builder
    public PrescriptionCreateDto(String title, String description, Long boardId, String isbn){
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.boardId = boardId;
    }

    public Prescription toEntity(Client client, Book book, Board board){
        return Prescription.builder()
                .title(title)
                .description(description)
                .board(board)
                .book(book)
                .client(client)
                .build();
    }

}
