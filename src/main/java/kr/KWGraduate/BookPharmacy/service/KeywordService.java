package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.keyword.BookKeywordDTO;
import kr.KWGraduate.BookPharmacy.dto.keyword.ClientKeywordDTO;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import kr.KWGraduate.BookPharmacy.exception.status.AllException;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.KeywordItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {
    private final KeywordItemRepository keywordItemRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    public BookKeywordDTO getBookKeywords(String isbn){
        bookRepository.findOptionalByIsbn(isbn)
                .orElseThrow(() -> new AllException("해당 isbn의 책이 존재하지 않습니다") {
                    @Override
                    public String getErrorMessage() {
                        return super.getErrorMessage();
                    }
                });

        List<KeywordItem> byIsbn = keywordItemRepository.findByIsbn(isbn);
        return BookKeywordDTO.builder().isbn(isbn).keywordItems(byIsbn).build();
    }

    public ClientKeywordDTO getClientKeywords(Long id){
        clientRepository.findById(id)
                .orElseThrow(() -> new AllException("해당 id의 유저가 없습니다.") {
                    @Override
                    public String getErrorMessage() {
                        return super.getErrorMessage();
                    }
                });
        List<KeywordItem> byId = keywordItemRepository.findByClientId(id);
        return ClientKeywordDTO.builder().id(id).keywordItems(byId).build();
    }
}
