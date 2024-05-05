package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"title", "description", "keyword"})
@Table(indexes = {
        @Index(name = "one_line_prescription_title_index", columnList = "title"),
        @Index(name = "one_line_prescription_description_index", columnList = "description"),
        @Index(name = "one_line_prescription_keyword_index", columnList = "keyword")
})
public class OneLinePrescription extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_line_prescription_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Keyword keyword;

    @Builder
    public OneLinePrescription(Client client, Book book, String title, String description, Keyword keyword) {
        this.client = client;
        this.book = book;
        this.title = title;
        this.description = description;
        this.keyword = keyword;
    }

    public void setClientAndBook(Client client, Book book) {
        this.client = client;
        this.book = book;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setBook(Book book){
        this.book = book;
    }
}
