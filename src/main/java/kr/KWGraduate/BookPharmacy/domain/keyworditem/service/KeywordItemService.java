package kr.KWGraduate.BookPharmacy.domain.keyworditem.service;

import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.KeywordItem;
import kr.KWGraduate.BookPharmacy.global.common.error.AllException;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.repository.KeywordItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordItemService {
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
