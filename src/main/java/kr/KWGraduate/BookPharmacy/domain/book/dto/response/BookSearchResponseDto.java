package kr.KWGraduate.BookPharmacy.domain.book.dto.response;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookSearchResponseDto {
    private String title;
    private String author; // 저자명
    private String publishingHouse; // 출판사명
    private String publishYear; // 발행년도
    private Long bookId; // 책 id (책 조회할 때 pk로 쓰임)
    private String imageUrl; // 이미지 url

    @Builder
    public BookSearchResponseDto(String title, String author, String publishingHouse, String publishYear, Long bookId, String imageUrl) {
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.publishYear = publishYear;
        this.bookId = bookId;
        this.imageUrl = imageUrl;
    }

    public BookSearchResponseDto(Book book) {
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishingHouse = book.getPublishingHouse();
        this.publishYear = book.getPublishYear();
        this.bookId = book.getId();
        this.imageUrl = book.getImageUrl();
    }

    /**
     * Book리스트를 BookSearchDto리스트로 변환하는 함수
     * */
    public static List<BookSearchResponseDto> toDtoList(List<Book> bookList) {
        List<BookSearchResponseDto> bookSearhDtoList = bookList.stream().map(book -> new BookSearchResponseDto(book))
                .collect(Collectors.toList());
        return bookSearhDtoList;
    }
}
