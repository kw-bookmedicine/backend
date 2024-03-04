package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FeedDto {

    private String bookTitle;
    private String bookAuthor;
    private String bookPublishYear;
    private String bookIsbn;
    private String clientNickname;
    private String comment;
    private float rating;
    private LocalDateTime registerDateTime;
    private LocalDateTime lastModifiedDate;

    public FeedDto(){

    }

    @Builder
    public FeedDto(String bookTitle, String bookAuthor, String bookPublishYear, String bookIsbn, String clientNickname, String comment, float rating, LocalDateTime registerDateTime, LocalDateTime lastModifiedDate) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublishYear = bookPublishYear;
        this.bookIsbn = bookIsbn;
        this.clientNickname = clientNickname;
        this.comment = comment;
        this.rating = rating;
        this.registerDateTime = registerDateTime;
        this.lastModifiedDate = lastModifiedDate;
    }

    public FeedDto setBookAttr(Book book){
        this.bookTitle = book.getTitle();
        this.bookAuthor = book.getAuthor();
        this.bookPublishYear = book.getPublishYear();
        this.bookIsbn = book.getIsbn();
        return this;
    }

    public FeedDto setClientAttr(Client client){
        this.clientNickname = client.getNickname();

        return this;
    }

    public FeedDto setFeedAttr(Feed feed) {
        this.comment = feed.getComment();
        this.rating = feed.getRating();
        this.registerDateTime = feed.getCreatedDate();
        this.lastModifiedDate = feed.getLastModifiedDate();

        return this;
    }

    public static Feed toEntity(FeedDto feedDto, Client client, Book book){
        Feed feed = Feed.builder()
                .comment(feedDto.getComment())
                .rating(feedDto.getRating())
                .build();
        feed.setClientAndBook(client,book);

        return feed;
    }
}
