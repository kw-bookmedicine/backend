package kr.KWGraduate.BookPharmacy.domain.book.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRecommend {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_recommend_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommending_book_id")
    private Book recommendingBook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommended_book_id")
    private Book recommendedBook;
}
