package kr.KWGraduate.BookPharmacy.domain.answer.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(of = {"id" , "question","answer"})
@Table(indexes = {
    @Index(name = "answer_question_index", columnList = "question")
})
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    private String answer;

    @Builder
    public Answer(String question, Board board, String answer){
        this.question = question;
        this.board = board;
        this.answer = answer;
    }

    public void update(String answer){
        this.answer = answer;
    }

}
