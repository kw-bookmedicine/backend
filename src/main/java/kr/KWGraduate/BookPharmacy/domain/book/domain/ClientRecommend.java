package kr.KWGraduate.BookPharmacy.domain.book.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientRecommend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private Integer rank;

    @Builder
    public ClientRecommend(Client client, Book book, Integer rank){
        this.client = client;
        this.book = book;
        this.rank = rank;
    }

}
