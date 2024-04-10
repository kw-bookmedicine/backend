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
    private Long bookId;
    private Long boardId;

    @Builder
    public PrescriptionCreateDto(String title, String description, Long boardId, Long bookId){
        this.title = title;
        this.description = description;
        this.bookId = bookId;
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
