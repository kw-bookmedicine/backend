package kr.KWGraduate.BookPharmacy.domain.book.service;

import jakarta.annotation.PostConstruct;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
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
        Book book = bookRepository.findBookDetailWithKeywordByIsbn(isbn);

        BookDto bookDto = BookDto.toDtoWithKeywordDto(book);

        return bookDto;
    }
}
