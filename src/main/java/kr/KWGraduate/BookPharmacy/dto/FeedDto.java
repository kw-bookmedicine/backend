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
    private String clientNickname;
    private String comment;
    private float rating;
    private LocalDateTime registerDateTime;
    private LocalDateTime modifyDateTime;

    public FeedDto(){

    }

    @Builder
    public FeedDto(String bookTitle, String bookAuthor, String bookPublishYear, String clientNickname, String comment, float rating, LocalDateTime registerDateTime, LocalDateTime modifyDateTime) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublishYear = bookPublishYear;
        this.clientNickname = clientNickname;
        this.comment = comment;
        this.rating = rating;
        this.registerDateTime = registerDateTime;
        this.modifyDateTime = modifyDateTime;
    }

    public FeedDto setBookAttr(Book book){
        this.bookTitle = book.getTitle();
        this.bookAuthor = book.getAuthor();
        this.bookPublishYear = book.getPublishYear();

        return this;
    }

    public FeedDto setClientAttr(Client client){
        this.clientNickname = client.getNickname();

        return this;
    }

    public FeedDto setFeedAttr(Feed feed){
        this.comment = feed.getComment();
        this.rating = feed.getRating();
        this.registerDateTime = feed.getRegisterDateTime();
        this.modifyDateTime = feed.getModifyDateTime();

        return this;
    }

    public static Feed toEntity(FeedDto feedDto){
        Feed feed = Feed.builder()
                .comment(feedDto.getComment())
                .rating(feedDto.getRating())
                .registerDateTime(feedDto.getRegisterDateTime())
                .modifyDateTime(feedDto.getModifyDateTime())
                .build();

        return feed;
    }
}
