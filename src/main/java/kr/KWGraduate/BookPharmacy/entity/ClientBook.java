package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ClientBook {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_book_id")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    public Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    public Book book;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id")
    public Feed feed;

    public ClientBook() {
    }

    /**
     * 연관관계 편의 메서드 ( Client <- ClientBook -> Book )
     */
    public void setClientAndBook(Client client, Book book){
        addClientBookForBook(book);
        this.book = book;
        addClientBookForClient(client);
        this.client = client;
    }

    private void addClientBookForBook(Book book){
        if(!book.getClientBooks().contains(this)){
            book.getClientBooks().add(this);
        }
    }

    private void addClientBookForClient(Client client){
        if(!client.getClientBooks().contains(this)){
            client.getClientBooks().add(this);
        }
    }

    /**
     * 연관관계 편의 메서드 ( ClientBook <-> Feed )
     */
    public void updateFeed(Feed feed) {
        this.feed = feed;
        feed.setClientbook(this);
    }
}
