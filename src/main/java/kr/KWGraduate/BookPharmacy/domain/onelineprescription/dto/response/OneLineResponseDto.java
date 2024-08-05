package kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class OneLineResponseDto {
    private Long id;
    private String title;
    private String description;
    private Keyword keyword;
    private String bookTitle;
    private Long bookId;
    private String bookAuthor;
    private String bookImageUrl;
    private String bookPublishYear;
    private String bookPublishingHouse;
    private String clientNickname;
    private boolean isLike;
    private boolean isHelpful;
    private long likeCount;
    private long helpfulCount;
    private LocalDate createdDate;

    @Builder
    public OneLineResponseDto(Long id, String title, String description, Keyword keyword, String bookTitle, Long bookId, String bookAuthor, String bookImageUrl,
                              String bookPublishYear, String bookPublishingHouse, boolean isLike, boolean isHelpful, Integer likeCount, Integer helpfulCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.keyword = keyword;
        this.bookTitle = bookTitle;
        this.bookId = bookId;
        this.bookAuthor = bookAuthor;
        this.bookImageUrl = bookImageUrl;
        this.bookPublishYear = bookPublishYear;
        this.bookPublishingHouse = bookPublishingHouse;
        this.isLike = isLike;
        this.isHelpful = isHelpful;
        this.likeCount = likeCount;
        this.helpfulCount = helpfulCount;
    }

    public OneLineResponseDto setAllAttr(Book book, Client client, OneLinePrescription oneLinePrescription) {
        this.setBookAttr(book);
        this.setClientAttr(client);
        this.setOneLinePrescriptionAttr(oneLinePrescription);

        return this;
    }

    private OneLineResponseDto setBookAttr(Book book) {
        this.bookTitle = book.getTitle();
        this.bookId = book.getId();
        this.bookAuthor = book.getAuthor();
        this.bookImageUrl = book.getImageUrl();
        this.bookPublishYear = book.getPublishYear();
        this.bookPublishingHouse = book.getPublishingHouse();

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
        this.keyword = oneLinePrescription.getKeyword();
        this.createdDate = oneLinePrescription.getCreatedDate().toLocalDate();
        this.likeCount = oneLinePrescription.getLikeCount();
        this.helpfulCount = oneLinePrescription.getHelpfulCount();

        return this;
    }

    public OneLineResponseDto setIsLike(boolean like) {
        isLike = like;
        return this;
    }

    public OneLineResponseDto setIsHelpful(boolean helpful) {
        isHelpful = helpful;
        return this;
    }
}
