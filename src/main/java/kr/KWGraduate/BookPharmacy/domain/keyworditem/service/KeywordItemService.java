package kr.KWGraduate.BookPharmacy.domain.keyworditem.service;

import kr.KWGraduate.BookPharmacy.domain.book.exception.BookResourceNotFoundException;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.exception.NoExistIdException;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.KeywordItem;
import kr.KWGraduate.BookPharmacy.global.common.error.BusinessException;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.repository.KeywordItemRepository;
import kr.KWGraduate.BookPharmacy.global.common.error.ErrorCode;
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

    public List<KeywordItemDto> getBookKeywords(Long bookId){
        bookRepository.findOptionalById(bookId)
                .orElseThrow(() -> new BookResourceNotFoundException(bookId));

        List<KeywordItem> keywordItemList = keywordItemRepository.findByBookId(bookId);

        List<KeywordItemDto> keywordItemDtoList = KeywordItemDto.toDtoList(keywordItemList);
        return keywordItemDtoList;
    }

    public List<KeywordItemDto> getClientKeywords(String loginId){
        Client client = clientRepository.findByLoginId(loginId)
                .orElseThrow(() -> new NoExistIdException(loginId));
        List<KeywordItem> keywordItemList = keywordItemRepository.findByClientId(client.getId());

        List<KeywordItemDto> keywordItemDtoList = KeywordItemDto.toDtoList(keywordItemList);

        return keywordItemDtoList;
    }
}
