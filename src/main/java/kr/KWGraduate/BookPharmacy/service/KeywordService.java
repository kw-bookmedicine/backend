package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import kr.KWGraduate.BookPharmacy.exception.status.AllException;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
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
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    public List<KeywordItemDto> getBookKeywords(String isbn){
        bookRepository.findOptionalByIsbn(isbn)
                .orElseThrow(() -> new AllException("해당 isbn의 책이 존재하지 않습니다") {
                    @Override
                    public String getErrorMessage() {
                        return super.getErrorMessage();
                    }
                });

        List<KeywordItem> keywordItemList = keywordItemRepository.findByIsbn(isbn);

        List<KeywordItemDto> keywordItemDtoList = KeywordItemDto.toDtoList(keywordItemList);
        return keywordItemDtoList;
    }

    public List<KeywordItemDto> getClientKeywords(Long id){
        clientRepository.findById(id)
                .orElseThrow(() -> new AllException("해당 id의 유저가 없습니다.") {
                    @Override
                    public String getErrorMessage() {
                        return super.getErrorMessage();
                    }
                });
        List<KeywordItem> keywordItemList = keywordItemRepository.findByClientId(id);

        List<KeywordItemDto> keywordItemDtoList = KeywordItemDto.toDtoList(keywordItemList);

        return keywordItemDtoList;
    }
}
