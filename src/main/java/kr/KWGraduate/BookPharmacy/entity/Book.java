package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "big_category_id")
    private Categories bigCategories;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "middle_category_id")
    private Categories middleCategories;

    @Builder
    public Book(String isbn, String title, String author, Categories bigCategories, Categories middleCategories) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.bigCategories = bigCategories;
        this.middleCategories = middleCategories;
    }

}
