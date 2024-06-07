package kr.KWGraduate.BookPharmacy.domain.book.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InterestRecommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories middleCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public InterestRecommend(Book b) {
        this.middleCategory = b.getMiddleCategory();
        this.book = b;
    }
}
