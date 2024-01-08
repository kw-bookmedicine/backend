package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
public class BookKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_keyword_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private KeywordItem keywordItem;

    public BookKeyword() {
    }

    /**
    * 연관관계 편의 메서드
     */
    public void setBookAndKeyword(Book book, KeywordItem keywordItem){
        addBookKeyWordForBook(book);
        this.book = book;
        addBookKeyWordForKeyword(keywordItem);
        this.keywordItem = keywordItem;
    }

    private void addBookKeyWordForBook(Book book){
        if(!book.getBookKeywords().contains(this)){
            book.getBookKeywords().add(this);
        }
    }

    private void addBookKeyWordForKeyword(KeywordItem keywordItem){
        if(!keywordItem.getBookKeywords().contains(this)){
            keywordItem.getBookKeywords().add(this);
        }
    }


}
