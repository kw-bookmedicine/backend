package kr.KWGraduate.BookPharmacy.domain.category.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name"})
public class Categories {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private Categories parentCategory;

    private int level;

    @Builder
    public Categories(String name, Categories parentCategory, int level) {
        this.name = name;
        this.parentCategory = parentCategory;
        this.level = level;
    }
}
