package kr.KWGraduate.BookPharmacy.domain.book.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRecommend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_recommend_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Board board;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
}

