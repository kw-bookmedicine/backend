package kr.KWGraduate.BookPharmacy.domain.keyworditem.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "count"})
@Table(indexes = {
        @Index(name = "keyword_count_index",columnList = "count")
})
public class KeywordItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    private String name;

    private Long count;

    @OneToMany(mappedBy = "keywordItem", cascade = CascadeType.ALL)
    private List<BookKeyword> bookKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "keywordItem", cascade = CascadeType.ALL)
    private List<ClientKeyword> clientKeywords = new ArrayList<>();

    @Builder
    public KeywordItem(String name) {
        this.name = name;
        this.count = count;
    }
}
