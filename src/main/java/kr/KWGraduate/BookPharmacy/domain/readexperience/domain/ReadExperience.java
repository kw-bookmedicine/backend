package kr.KWGraduate.BookPharmacy.domain.readexperience.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.global.common.BaseTimeEntity;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "read_experience_index", columnList = "client_id, book_id", unique = true)
})
public class ReadExperience {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "read_experience_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Builder
    public ReadExperience(Book book, Client client){
        this.book = book;
        this.client = client;
    }
}
