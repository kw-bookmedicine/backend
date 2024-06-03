package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BestSellerBookDto;
import kr.KWGraduate.BookPharmacy.domain.book.service.BestSellerBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BestSellerBookServiceTest {

    @Autowired
    BestSellerBookService bestSellerBookService;

    @Test
    void getBestSellerBooks() {
        List<BestSellerBookDto> bestSellerBooks = bestSellerBookService.getBestSellerBookList();

        assertThat(bestSellerBooks.size()).isEqualTo(10);
    }

    @Test
    void setBestSellerBooks() {
        bestSellerBookService.setBestSellerBooks();
    }
}