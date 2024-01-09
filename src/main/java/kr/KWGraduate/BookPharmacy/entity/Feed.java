package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Feed {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean isRated;

    private String comment;

    private float rating;

    private LocalDateTime registerDateTime;

    private LocalDateTime modifyDateTime;

    @Builder
    public Feed(boolean isRated, String comment, float rating, LocalDateTime registerDateTime, LocalDateTime modifyDateTime) {
        this.isRated = isRated;
        this.comment = comment;
        this.rating = rating;
        this.registerDateTime = registerDateTime;
        this.modifyDateTime = modifyDateTime;
    }

    public Feed() {
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
