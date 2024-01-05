package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.BookSearchDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


    public List<BookDto> getBookListByMiddleCategory(int pageNumber, int pageSize, String categoryName){

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPageList = bookRepository.findBookPagingByMiddleCategory(categoryName, pageRequest);

        List<Book> bookList = bookPageList.getContent();
        List<BookDto> bookDtoList = toDtoList(bookList);

        return bookDtoList;
    }

    /**
     * Book 리스트를 BookDto 리스트로 변환하는 함수  ---> 사용할 일이 없지 않을까..?
     * */
    public List<BookDto> toDtoList(List<Book> books){
        List<BookDto> bookDtoList = books.stream()
                .map(book -> toDto(book))
                .collect(Collectors.toList());

        return bookDtoList;
    }

    /**
     * Book를 BookDto으로 변환하는 함수
     * */
    public BookDto toDto(Book book){
        BookDto bookDto = new BookDto(book);

        return bookDto;
    }

    /**
     * BookDto 리스트를 Book 리스트로 변환하는 함수  ---> 사용할 일이 없지 않을까..?
     * */
    public List<Book> toBookEntityList(List<BookDto> bookDtos){
        List<Book> bookList = bookDtos.stream()
                .map(dto -> toBookEntity(dto))
                .collect(Collectors.toList());

        return bookList;
    }

    /**
     * BookDto를 Book으로 변환하는 함수
     * */
    public Book toBookEntity(BookDto bookDto){
        Book book = Book.builder()
                        .title(bookDto.getTitle())
                        .author(bookDto.getAuthor())
                        .isbn(bookDto.getIsbn())
                        .content(bookDto.getContent())
                        .build();

        return book;
    }

    /**
     * 검색어와 paging size를 입력받으면, 해당하는 책 dto 리스트를 반환
     */
    public List<BookSearchDto> searchBySearchWord(int pageNumber, int pageSize, String searchKeyword){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPageList = bookRepository.findPagingByTitleContaining(searchKeyword, pageRequest);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchDto> bookSearchDtoList = toSearchDtoList(bookList);

        return bookSearchDtoList;
    }

    /**
     * Book리스트를 BookSearchDto리스트로 변환하는 함수
     * */
    private List<BookSearchDto> toSearchDtoList(List<Book> bookList) {
        List<BookSearchDto> bookSearhDtoList = bookList.stream().map(book -> new BookSearchDto(book))
                .collect(Collectors.toList());
        return bookSearhDtoList;
    }
}
