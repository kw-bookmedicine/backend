package kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.response;

import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.OneLinePrescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OneLineResponseDto {
    private Long id;
    private String title;
    private String description;
    private String bookTitle;
    private String bookIsbn;
    private String bookAuthor;
    private String bookImageUrl;
    private String clientNickname;

    @Builder
    public OneLineResponseDto(Long id, String title, String description, String bookTitle, String bookIsbn, String bookAuthor, String bookImageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bookTitle = bookTitle;
        this.bookIsbn = bookIsbn;
        this.bookAuthor = bookAuthor;
        this.bookImageUrl = bookImageUrl;
    }

    public OneLineResponseDto setAllAttr(Book book, Client client, OneLinePrescription oneLinePrescription) {
        this.setBookAttr(book);
        this.setClientAttr(client);
        this.setOneLinePrescriptionAttr(oneLinePrescription);

        return this;
    }

    private OneLineResponseDto setBookAttr(Book book) {
        this.bookTitle = book.getTitle();
        this.bookIsbn = book.getIsbn();
        this.bookAuthor = book.getAuthor();
        this.bookImageUrl = book.getImageUrl();

        return this;
    }

    private OneLineResponseDto setClientAttr(Client client) {
        this.clientNickname = client.getNickname();

        return this;
    }

    private OneLineResponseDto setOneLinePrescriptionAttr(OneLinePrescription oneLinePrescription) {
        this.id = oneLinePrescription.getId();
        this.title = oneLinePrescription.getTitle();
        this.description = oneLinePrescription.getDescription();

        return this;
    }
}
