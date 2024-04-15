package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id" , "question","answer"})
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String answer;

    @Builder
    public Answer(String question, Board board, String answer){
        this.question = question;
        this.board = board;
        this.answer = answer;
    }

}
