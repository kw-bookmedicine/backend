package kr.KWGraduate.BookPharmacy.domain.book.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.BookKeyword;
import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"isbn", "title", "author", "bigCategory", "middleCategory", "imageUrl", "count"})
@Table(indexes = {
        @Index(name = "book_isbn_index", columnList = "isbn", unique = true),
        @Index(name = "book_count_index", columnList = "count")
})
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;
    @NotNull
    private String isbn; // 일련번호
    private String title; // 책이름
    private String author; // 저자명
    private String publishingHouse; // 출판사명
    private String publishYear; // 발행년도
    private String content; // 책내용
    private float rating; // 평균 평점
    private int reviewNum; // 리뷰 개수
    private String mediaFlagNumber; // 미디어구분명
    private String imageUrl; // 이미지 url
    private int count; // 조회된 횟수

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookKeyword> bookKeywords = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "big_category_id")
    private Categories bigCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "middle_category_id")
    private Categories middleCategory;

    @Builder
    public Book(String isbn, String title, String author, String publishingHouse, String publishYear, String content, float rating,
                int reviewNum, Categories bigCategory, Categories middleCategory, String imageUrl, int count) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.publishYear = publishYear;
        this.content = content;
        this.rating = rating;
        this.reviewNum = reviewNum;
        this.bigCategory = bigCategory;
        this.middleCategory = middleCategory;
        this.imageUrl = imageUrl;
        this.count = count;
    }
}
