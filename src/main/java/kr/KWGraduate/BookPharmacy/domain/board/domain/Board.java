package kr.KWGraduate.BookPharmacy.domain.board.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.global.common.BaseTimeEntity;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id" , "title","description","keyword","status"})
@Table(indexes = {
        @Index(name = "board_keyword_index", columnList = "keyword"),
        @Index(name = "board_created_date_index", columnList = "created_date")
})
public class Board extends BaseTimeEntity {
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



    @Builder
    public Board(Client client, String title, String description, Keyword keyword){
        this.client = client;
        this.title = title;
        this.description =description;
        this.keyword = keyword;
    }


    public void modifyBoard(String title, String description,Keyword keyword){
        this.title = title;
        this.description =description;
        this.keyword = keyword;
    }

}
