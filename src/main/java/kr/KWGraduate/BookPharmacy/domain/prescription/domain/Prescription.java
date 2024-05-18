package kr.KWGraduate.BookPharmacy.domain.prescription.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.global.common.BaseTimeEntity;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = {
        @Index(name = "prescription_created_date_index", columnList = "created_date")
})
@ToString(of = {"id" , "title" , "description"})
public class Prescription extends BaseTimeEntity {
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
    @OnDelete(action = OnDeleteAction.CASCADE)
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
