package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.keyword.BookKeywordDTO;
import kr.KWGraduate.BookPharmacy.dto.keyword.ClientKeywordDTO;
import kr.KWGraduate.BookPharmacy.entity.*;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class KeywordServiceTest {
    @Autowired
    private KeywordService keywordService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void getBookKeywords(){
        Book book = bookRepository.findById(12L).get();

        List<KeywordItem> keywordItems = new ArrayList<>();

        for(BookKeyword bk : book.getBookKeywords()){
            keywordItems.add(bk.getKeywordItem());
        }

        BookKeywordDTO bookKeywordDTO = BookKeywordDTO.builder().isbn(book.getIsbn()).keywordItems(keywordItems).build();

        BookKeywordDTO findBookKeywords = keywordService.getBookKeywords(book.getIsbn());

        Assertions.assertThat(bookKeywordDTO).isEqualTo(findBookKeywords);

    }

    @Test
    public void getClientKeywords(){
        Client client = clientRepository.findById(1L).get();

        List<KeywordItem> keywordItems = new ArrayList<>();

        for(ClientKeyword ck : client.getClientKeywords()){
            keywordItems.add(ck.getKeywordItem());
        }

        ClientKeywordDTO clientKeywordDTO = ClientKeywordDTO.builder().id(client.getId()).keywordItems(keywordItems).build();
        ClientKeywordDTO findClientKeywordDTO = keywordService.getClientKeywords(client.getId());

        Assertions.assertThat(clientKeywordDTO).isEqualTo(findClientKeywordDTO);
    }

}