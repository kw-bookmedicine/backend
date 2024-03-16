package kr.KWGraduate.BookPharmacy.service;

import jakarta.annotation.PostConstruct;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public List<BookDto> getBookListByMiddleCategory(String categoryName, Pageable pageable){

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPageList = bookRepository.findBookPagingByMiddleCategory(categoryName, pageRequest);

        List<Book> bookList = bookPageList.getContent();
        List<BookDto> bookDtoList = BookDto.toDtoList(bookList);

        return bookDtoList;
    }

    public BookDto getBookDetails(String isbn){
        Optional<Book> optional = bookRepository.findOptionalByIsbn(isbn);

        BookDto bookDto = optional.map(book -> new BookDto(book))
                .orElseGet(() -> BookDto.builder().title("해당 책이 없습니다.").build());

        return bookDto;
    }
}
