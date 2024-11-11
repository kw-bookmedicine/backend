package kr.KWGraduate.BookPharmacy.domain.prescription.dto.request;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.prescription.domain.Prescription;
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
