package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"isbn", "title", "author", "bigCategory", "middleCategory"})
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    private String isbn; // 일련번호
    private String title; // 책이름
    private String author; // 저자명
    private String publicYear; // 발행년도
    private String content; // 책내용
    private String mediaFlagNumber; // 미디어구분명

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ClientBook> clientBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookKeyword> bookKeywords = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "big_category_id")
    private Categories bigCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "middle_category_id")
    private Categories middleCategory;

    @Builder
    public Book(String isbn, String title, String author, String content, Categories bigCategory, Categories middleCategory) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.content = content;
        this.bigCategory = bigCategory;
        this.middleCategory = middleCategory;
    }

}
