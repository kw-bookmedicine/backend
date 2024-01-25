package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.dto.keyword.BookKeywordDTO;
import kr.KWGraduate.BookPharmacy.entity.*;
import kr.KWGraduate.BookPharmacy.service.BookService;
import kr.KWGraduate.BookPharmacy.service.ClientService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class KeywordItemRepositoryTest {
    @Autowired
    private KeywordItemRepository keywordItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void findByIsbn(){

        Book book = bookRepository.findById(12L).get();

        List<KeywordItem> keywordItems = new ArrayList<>();

        for(BookKeyword bk : book.getBookKeywords()){
            keywordItems.add(bk.getKeywordItem());
        }

        List<KeywordItem> byIsbn = keywordItemRepository.findByIsbn(book.getIsbn());


        Assertions.assertThat(keywordItems).isEqualTo(byIsbn);

    }


    @Test
    public void findByClientId(){
        Client client = clientRepository.findById("1").get();

        List<KeywordItem> keywordItems = new ArrayList<>();

        for(ClientKeyword ck : client.getClientKeywords()){
            keywordItems.add(ck.getKeywordItem());
        }

        List<KeywordItem> byClientId = keywordItemRepository.findByClientId(1L);

        Assertions.assertThat(keywordItems).isEqualTo(byClientId);
    }

}