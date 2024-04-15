package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.BookSearchDto;
import kr.KWGraduate.BookPharmacy.dto.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.KeywordItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final BookRepository bookRepository;
    private final KeywordItemRepository keywordItemRepository;


    /**
     * 검색어와 paging size를 입력받으면, 검색어를 책이름에 포함하는 책 dto 리스트를 반환 (모달창)
     */
    public List<BookSearchDto> searchBookOnModalByTitleContainingSearchWord(String searchWord, Pageable pageable){

        Page<Book> bookPageList = bookRepository.findPagingByTitleContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchDto> bookSearchDtoList = BookSearchDto.toDtoList(bookList);

        return bookSearchDtoList;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 책이름에 포함하는 책 dto 리스트를 반환 (페이지)
     */
    public List<BookDto> searchBookOnPageByTitleContainingSearchWord(String searchWord, Pageable pageable){

        Page<Book> bookPageList = bookRepository.findPagingByTitleContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookDto> bookDtoList = BookDto.toDtoListWithKeywordDto(bookList);

        return bookDtoList;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 작가명에 포함하는 책 dto 리스트를 반환 (모달창)
     */
    public List<BookSearchDto> searchBookOnModalByAuthorContainingSearchWord(String searchWord, Pageable pageable){

        Page<Book> bookPageList = bookRepository.findPagingByAuthorContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchDto> bookSearchDtoList = BookSearchDto.toDtoList(bookList);

        return bookSearchDtoList;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 작가명에 포함하는 책 dto 리스트를 반환 (페이지)
     */
    public List<BookDto> searchBookOnPageByAuthorContainingSearchWord(String searchWord, Pageable pageable){

        Page<Book> bookPageList = bookRepository.findPagingByAuthorContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookDto> bookDtoList = BookDto.toDtoListWithKeywordDto(bookList);

        return bookDtoList;
    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 포함하는 키워드 dto 리스트를 반환
     */
    public List<KeywordItemDto> searchKeywordBySearchWord(String searchWord, Pageable pageable){

        Page<KeywordItem> keywordPageList = keywordItemRepository.findByNameContaining(searchWord, pageable);

        List<KeywordItem> keywordList = keywordPageList.getContent();
        List<KeywordItemDto> keywordItemDtoList = KeywordItemDto.toDtoList(keywordList);

        return keywordItemDtoList;
    }
}
