package kr.KWGraduate.BookPharmacy.domain.book.service;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookSearchService {

    private final BookRepository bookRepository;


    /**
     * 검색어와 paging size를 입력받으면, 검색어를 책이름에 포함하는 책 dto 리스트를 반환 (모달창)
     */
    public List<BookSearchResponseDto> searchBookOnModalByTitleContainingSearchWord(String searchWord, Pageable pageable) {

        Page<Book> bookPageList = bookRepository.findPagingByTitleContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchResponseDto> bookSearchResponseDtoList = BookSearchResponseDto.toDtoList(bookList);

        return bookSearchResponseDtoList;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 책이름에 포함하는 책 dto 페이지를 반환 (페이지)
     */
    public Page<BookDto> searchBookOnPageByTitleContainingSearchWord(String searchWord, Pageable pageable) {

        Page<Book> bookPageList = bookRepository.findPagingByTitleContaining(searchWord, pageable);

        Page<BookDto> bookDtoPage = BookDto.toDtoPageWithKeywordDto(bookPageList);

        return bookDtoPage;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 작가명에 포함하는 책 dto 리스트를 반환 (모달창)
     */
    public List<BookSearchResponseDto> searchBookOnModalByAuthorContainingSearchWord(String searchWord, Pageable pageable) {

        Page<Book> bookPageList = bookRepository.findPagingByAuthorContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchResponseDto> bookSearchResponseDtoList = BookSearchResponseDto.toDtoList(bookList);

        return bookSearchResponseDtoList;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 작가명에 포함하는 책 dto 페이지를 반환 (페이지)
     */
    public Page<BookDto> searchBookOnPageByAuthorContainingSearchWord(String searchWord, Pageable pageable) {

        Page<Book> bookPageList = bookRepository.findPagingByAuthorContaining(searchWord, pageable);

        Page<BookDto> bookDtoPage = BookDto.toDtoPageWithKeywordDto(bookPageList);

        return bookDtoPage;
    }

    /**
     * 검색어와 paging size, 그리고 keywordList를 입력받으면, 검색어를 책이름 또는 작가명에 포함하는 책 중 키워드를 포함하는 책 dto 페이지를 반환
     */
    public Page<BookDto> searchBookBySearchAndKeyword(String searchWord, List<String> keywordList, Pageable pageable) {

        Page<Book> bookPageList = bookRepository.findPagingBySearchWordAndKeyword(searchWord, keywordList, pageable);

        Page<BookDto> bookDtoPage = BookDto.toDtoPageWithKeywordDto(bookPageList);

        return bookDtoPage;
    }

}