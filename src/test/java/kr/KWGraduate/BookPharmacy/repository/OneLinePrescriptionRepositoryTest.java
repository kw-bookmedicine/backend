package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLineLikeEmotionRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLinePrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class OneLinePrescriptionRepositoryTest {

    @Autowired OneLinePrescriptionRepository oneLinePrescriptionRepository;
    @Autowired
    OneLineLikeEmotionRepository oneLineLikeEmotionRepository;
    @Autowired ClientRepository clientRepository;
    @Autowired BookRepository bookRepository;

    @BeforeEach
    void 데이터_주입(){
        Client client1 = Client.builder().loginId("kw_lsh_3717").password("kw_lsh_password").nickname("리성훈").build();
        Client client2 = Client.builder().loginId("kw_sjy_3717").password("kw_sjy_password").nickname("심재윤").build();
        Client client3 = Client.builder().loginId("kw_lsj_3717").password("kw_lsj_password").nickname("리소정").build();

        clientRepository.saveAllAndFlush(List.of(client1, client2, client3));

        Book book1 = Book.builder().isbn("1234").title("이것은 경제 관련 책입니다.").author("경제학자 이성훈").build();
        Book book2 = Book.builder().isbn("5678").title("이것은 철학 관련 책입니다").author("철학자 심재윤").build();
        Book book3 = Book.builder().isbn("0001").title("경제 경영에 유명한 책입니다").author("경영학 이소정").build();
        Book book4 = Book.builder().isbn("0002").title("이것은 과학 책").author("아인슈타인").build();

        bookRepository.saveAllAndFlush(List.of(book1, book2, book3, book4));

        OneLinePrescription prescription1 = OneLinePrescription.builder()
                .client(client1)
                .book(book1)
                .title("경제 입문자 꼭 읽어보시길")
                .description("한번 읽어보시길")
                .keyword(Keyword.Economy_Management)
                .build();

        OneLinePrescription prescription2 = OneLinePrescription.builder()
                .client(client2)
                .book(book1)
                .title("경제 개초보도 읽을 수 있다.")
                .description("두번 읽어보시길")
                .keyword(Keyword.Economy_Management)
                .build();

        OneLinePrescription prescription3 = OneLinePrescription.builder()
                .client(client3)
                .book(book1)
                .title("경제 왕초보도 꼭 읽어보시길")
                .description("세번 읽어보시길")
                .keyword(Keyword.Economy_Management)
                .build();

        OneLinePrescription prescription4 = OneLinePrescription.builder()
                .client(client1)
                .book(book2)
                .title("철학 입문자 꼭 읽어보시길")
                .description("한번 읽어보시길")
                .keyword(Keyword.Philosophy)
                .build();

        OneLinePrescription prescription5 = OneLinePrescription.builder()
                .client(client3)
                .book(book2)
                .title("철학 개초보 꼭 읽어보시길")
                .description("두번 읽어보시길")
                .keyword(Keyword.Philosophy)
                .build();

        OneLinePrescription prescription6 = OneLinePrescription.builder()
                .client(client2)
                .book(book3)
                .title("경제경영 개꿀잼 꼭 읽어보시길")
                .description("한번 읽어보시길")
                .keyword(Keyword.Economy_Management)
                .build();

        OneLinePrescription prescription7 = OneLinePrescription.builder()
                .client(client3)
                .book(book3)
                .title("경제경영 학부생들도 한번쯤은 읽어보세요")
                .description("개추")
                .keyword(Keyword.Economy_Management)
                .build();

        OneLinePrescription prescription8 = OneLinePrescription.builder()
                .client(client1)
                .book(book4)
                .title("과학에 대해서 아무것도 모르는 사람한테 개추")
                .description("개추 완전 개추!!!!!!!!!!!")
                .keyword(Keyword.Science_Math_Engineering)
                .build();

        oneLinePrescriptionRepository.saveAllAndFlush(List.of(prescription1, prescription2, prescription3, prescription4,
                prescription5, prescription6, prescription7, prescription8));
    }

    @Test
    void 제목과_설명에_검색어를_포함하는_한줄처방_조회() {
        List<OneLinePrescription> result = oneLinePrescriptionRepository.findByTitleOrDescriptionContaining("개추");
        assertThat(2).isEqualTo(result.size());

        List<OneLinePrescription> result2 = oneLinePrescriptionRepository.findByTitleOrDescriptionContaining("개추!!!!!!!!!!!");
        assertThat("kw_lsh_3717").isEqualTo(result2.get(0).getClient().getLoginId());
    }

    @Test
    void 책의_isbn으로_한줄처방_조회() {
//        List<OneLinePrescription> result = oneLinePrescriptionRepository.findByBookIsbn("1234");
//        assertThat(3).isEqualTo(result.size());
    }

    @Test
    void 책의_키워드로_한줄처방_조회() {
//        List<OneLinePrescription> result = oneLinePrescriptionRepository.findByKeyword(Keyword.Economy_Management, Pageable );
//        assertThat(5).isEqualTo(result.size());
    }

    @Test
    void 책의_Id로_한줄처방_조회() {
        long countByOneLinePreId = oneLineLikeEmotionRepository.findCountByOneLinePreId(148L);

        System.out.println(countByOneLinePreId);
    }
}