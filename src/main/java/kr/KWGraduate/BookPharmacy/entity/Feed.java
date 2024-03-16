package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Feed extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean isRated;

    private String comment;

    private float rating;

    @Builder
    public Feed(boolean isRated, String comment, float rating) {
        this.isRated = isRated;
        this.comment = comment;
        this.rating = rating;
    }

    public Feed() {
        this.isRated = false;
        this.comment = null;
        this.rating = 0;
    }

    /**
     * 연관관계 편의 메서드 ( Client <- ClientBook -> Book )
     */
    public void setClientAndBook(Client client, Book book){
        addFeedForBook(book);
        this.book = book;
        addFeedForClient(client);
        this.client = client;
    }

    private void addFeedForBook(Book book){
        if(!book.getFeeds().contains(this)){
            book.getFeeds().add(this);
        }
    }

    private void addFeedForClient(Client client){
        if(!client.getFeeds().contains(this)){
            client.getFeeds().add(this);
        }
    }

}
