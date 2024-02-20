package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Book;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class BookDto {

    private String isbn;
    private String title;
    private String author; // 저자명
    private String publicYear; // 발행년도
    private String content; // 책내용
    private String mediaFlagNumber; // 미디어구분명
    private String bigCategoryName; // 대분류명
    private String middleCategoryName; // 중분류명
    private String imageUrl; // 이미지 URL

    public BookDto(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publicYear = book.getPublishYear();
        this.content = book.getContent();
        this.mediaFlagNumber = book.getMediaFlagNumber();
        this.bigCategoryName = book.getBigCategory().getName();
        this.middleCategoryName = book.getMiddleCategory().getName();
        this.imageUrl = book.getImageUrl();
    }

    @Builder
    public BookDto(String isbn, String title, String author, String publicYear, String content, String mediaFlagNumber, String bigCategoryName, String middleCategoryName, String imageUrl) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicYear = publicYear;
        this.content = content;
        this.mediaFlagNumber = mediaFlagNumber;
        this.bigCategoryName = bigCategoryName;
        this.middleCategoryName = middleCategoryName;
        this.imageUrl = imageUrl;
    }

    /**
     * Book 리스트를 BookDto 리스트로 변환하는 함수  ---> 사용할 일이 없지 않을까..?
     * */
    public static List<BookDto> toDtoList(List<Book> books){
        List<BookDto> bookDtoList = books.stream()
                .map(book -> toDto(book))
                .collect(Collectors.toList());

        return bookDtoList;
    }

    /**
     * Book를 BookDto으로 변환하는 함수
     * */
    public static BookDto toDto(Book book){
        BookDto bookDto = new BookDto(book);

        return bookDto;
    }

    /**
     * BookDto를 Book으로 변환하는 함수
     * */
    public static Book toBookEntity(BookDto bookDto){
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .content(bookDto.getContent())
                .imageUrl(bookDto.getImageUrl())
                .build();

        return book;
    }
}
