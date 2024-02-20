package kr.KWGraduate.BookPharmacy.service;

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

//    public List<BookDto> getSearchedBookByAuthor(String word){
//        // BookRepository에서 word를 작가명에 포함하는 책 리스트 6개를 불러옴
//        // + 추후에 조회수에 대한 count가 추가되면 조회수가 많은 순으로 불러와야 함
//
//        // BookDtoList로 반환
//
//    }

    /**
     * 검색어와 paging size를 입력받으면, 검색어를 책이름이나 작가에 포함하는 책 dto 리스트를 반환
     */
    public List<BookSearchDto> searchBookBySearchWord(String searchWord, Pageable pageable){

        Page<Book> bookPageList = bookRepository.findPagingByTitleContainingOrAuthorContaining(searchWord, pageable);

        List<Book> bookList = bookPageList.getContent();
        List<BookSearchDto> bookSearchDtoList = BookSearchDto.toDtoList(bookList);

        return bookSearchDtoList;
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
