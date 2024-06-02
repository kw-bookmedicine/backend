package kr.KWGraduate.BookPharmacy.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineCreateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLinePrescriptionRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.service.OneLinePrescriptionService;
import kr.KWGraduate.BookPharmacy.global.security.auth.dto.ClientDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OneLinePrescriptionServiceTest {

    @Autowired ClientRepository clientRepository;
    @Autowired BookRepository bookRepository;
    @Autowired OneLinePrescriptionService oneLinePrescriptionService;
    @Autowired OneLinePrescriptionRepository oneLinePrescriptionRepository;

    @PersistenceContext EntityManager em;

    @BeforeEach
    public void 데이터_주입() {
        Client client = Client.builder().loginId("kw_lsh_3717").password("kw_lsh_password").nickname("리성훈").build();
        clientRepository.saveAndFlush(client);

        Book book1 = Book.builder().isbn("1234").title("title2").author("author2").build();
        Book book2 = Book.builder().isbn("0001").title("title3").author("author3").build();

        bookRepository.saveAllAndFlush(List.of(book1, book2));

        OneLinePrescription oneLinePrescription = OneLinePrescription.builder()
                .title("컴공 3학년에게 추천")
                .description("컴공 3학년에게 추천합니다!!")
                .build();
        oneLinePrescription.setClientAndBook(client, book1);

        oneLinePrescriptionRepository.saveAndFlush(oneLinePrescription);
    }

    @Test
    void createOneLinePrescription() {
        // given
        Client client = clientRepository.findByLoginId("kw_lsh_3717").get();
        ClientDetails userDetails = new ClientDetails(client);

        OneLineCreateDto dto = OneLineCreateDto.builder()
                .bookIsbn("1234")
                .title("컴공 학생들에게 추천")
                .description("컴공 잘하고 싶은 사람들에게 추천합니다!!")
                .build();

        // when
        OneLinePrescription saveResult = oneLinePrescriptionService.createOneLinePrescription(dto, userDetails);
        em.clear();

        // then
        assertThat(saveResult.getTitle()).isEqualTo("컴공 학생들에게 추천");
        assertThat(saveResult.getDescription()).isEqualTo("컴공 잘하고 싶은 사람들에게 추천합니다!!");
    }

    @Test
    void updateOneLinePrescription() {
        // given
        Client client = clientRepository.findByLoginId("kw_lsh_3717").get();
        ClientDetails userDetails = new ClientDetails(client);

        PageRequest pageRequest = PageRequest.of(0, 8);

        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findByBookIsbn("1234", pageRequest).getContent().get(0);
        Long savedId = oneLinePrescription.getId();

        em.clear();

        // when
        OneLineUpdateDto oneLineUpdateDto = OneLineUpdateDto.builder()
                .bookIsbn("0001")
                .title("컴공 4학년에게 추천")
                .description("이게 진짜임")
                .build();


        // then
        oneLinePrescriptionService.updateOneLinePrescription(savedId, oneLineUpdateDto, userDetails);
        em.flush();

        OneLinePrescription result = oneLinePrescriptionRepository.findByBookIsbn("0001", pageRequest).getContent().get(0);

        assertThat(result.getTitle()).isEqualTo("컴공 4학년에게 추천");
        assertThat(result.getDescription()).isEqualTo("이게 진짜임");
        assertThat(result.getId()).isEqualTo(savedId);
    }

    @Test
    void deleteOneLinePrescription() {
        // given
        Client client = clientRepository.findByLoginId("kw_lsh_3717").get();
        ClientDetails userDetails = new ClientDetails(client);

        PageRequest pageRequest = PageRequest.of(0, 8);

        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findByBookIsbn("1234", pageRequest).getContent().get(0);
        Long id = oneLinePrescription.getId();

        //when
        oneLinePrescriptionService.deleteOneLinePrescription(id, userDetails);
        em.flush();

        List<OneLinePrescription> result = oneLinePrescriptionRepository.findByBookIsbn("1234", pageRequest).getContent();

        // then
        assertThat(result.size()).isEqualTo(0);
    }
}