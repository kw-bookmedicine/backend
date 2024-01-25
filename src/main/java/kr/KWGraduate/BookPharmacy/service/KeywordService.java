package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.keyword.BookKeywordDTO;
import kr.KWGraduate.BookPharmacy.dto.keyword.ClientKeywordDTO;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import kr.KWGraduate.BookPharmacy.repository.KeywordItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {
    private final KeywordItemRepository keywordItemRepository;

    public BookKeywordDTO getBookKeywords(String isbn){
        List<KeywordItem> byIsbn = keywordItemRepository.findByIsbn(isbn);
        return BookKeywordDTO.builder().isbn(isbn).keywordItems(byIsbn).build();
    }

    public ClientKeywordDTO getClientKeywords(Long id){
        List<KeywordItem> byId = keywordItemRepository.findByClientId(id);
        return ClientKeywordDTO.builder().id(id).keywordItems(byId).build();
    }
}
