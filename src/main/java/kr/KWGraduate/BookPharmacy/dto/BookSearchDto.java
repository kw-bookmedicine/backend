package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookSearchDto {
    private String title;
    private String author; // 저자명
    private String publishYear; // 발행년도
    private String isbn; // 책 고유번호 (책 조회할 때 pk로 쓰임)
    private String imageUrl; // 이미지 url

    @Builder
    public BookSearchDto(String title, String author, String publishYear, String isbn, String imageUrl) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
    }

    public BookSearchDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishYear = book.getPublishYear();
        this.isbn = book.getIsbn();
        this.imageUrl = book.getImageUrl();
    }

    /**
     * Book리스트를 BookSearchDto리스트로 변환하는 함수
     * */
    public static List<BookSearchDto> toDtoList(List<Book> bookList) {
        List<BookSearchDto> bookSearhDtoList = bookList.stream().map(book -> new BookSearchDto(book))
                .collect(Collectors.toList());
        return bookSearhDtoList;
    }
}
