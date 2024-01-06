package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name"})
public class KeywordItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "keywordItem", cascade = CascadeType.ALL)
    private List<BookKeyword> bookKeywords = new ArrayList<>();

    @OneToMany(mappedBy = "keywordItem", cascade = CascadeType.ALL)
    private List<ClientKeyword> clientKeywords = new ArrayList<>();

    @Builder
    public KeywordItem(String name) {
        this.name = name;
    }
}
