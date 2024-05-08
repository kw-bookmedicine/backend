package kr.KWGraduate.BookPharmacy.domain.keyworditem.service;

import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.request.BookSearchDto;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.KeywordItem;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.repository.KeywordItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordItemSearchService {

    private final KeywordItemRepository keywordItemRepository;


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
