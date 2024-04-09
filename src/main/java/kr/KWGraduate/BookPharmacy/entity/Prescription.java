package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = {
        @Index(name = "prescription_created_date_index", columnList = "created_date")
})
public class Prescription extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String title;

    private String description;

    @Builder
    public Prescription(Client client, Book book, Board board, String title, String description){
        this.client = client;
        this.book = book;
        this.board = board;
        this.title = title;
        this.description = description;
    }

    public void modifyPrescription(Book book, String title, String description){
        this.book = book;
        this.title = title;
        this.description = description;
    }

}
