package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.enums.Keyword;
import kr.KWGraduate.BookPharmacy.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id" , "title","description","keyword","status"})
@Table(indexes = {
        @Index(name = "board_keyword_index", columnList = "keyword"),
        @Index(name = "board_status_index", columnList = "status"),
        @Index(name = "board_created_date_index", columnList = "created_date")
})
public class Board extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Keyword keyword;

    @Enumerated(EnumType.STRING)
    private Status status;


    @Builder
    public Board(Client client, String title, String description, Keyword keyword){
        this.client = client;
        this.title = title;
        this.description =description;
        this.keyword = keyword;
        this.status = Status.PRESCRIBING;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public void modifyBoard(String title, String description,Keyword keyword){
        this.title = title;
        this.description =description;
        this.keyword = keyword;
        this.status = Status.PRESCRIBING;
    }
}
