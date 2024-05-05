package kr.KWGraduate.BookPharmacy.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.dto.client.ClientDetails;
import kr.KWGraduate.BookPharmacy.dto.readExperience.request.ReadExperienceCreateDto;
import kr.KWGraduate.BookPharmacy.dto.readExperience.request.ReadExperienceUpdateRequestDto;
import kr.KWGraduate.BookPharmacy.dto.readExperience.response.ReadExperienceResponseDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.ReadExperience;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.ReadExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class ReadExperienceServiceTest {

    @Autowired ClientRepository clientRepository;
    @Autowired BookRepository bookRepository;
    @Autowired ReadExperienceRepository readExperienceRepository;

    @Autowired ReadExperienceService readExperienceService;

    @PersistenceContext EntityManager em;

    @BeforeEach
    public void 데이터_주입() {
        Client client1 = Client.builder().loginId("kw_lsh_3717").password("kw_lsh_password").nickname("리성훈").build();
        Client client2 = Client.builder().loginId("kw_sjy_3717").password("kw_sjy_password").nickname("심재윤").build();
        Client client3 = Client.builder().loginId("kw_lsj_3717").password("kw_lsj_password").nickname("리소정").build();

        clientRepository.saveAllAndFlush(List.of(client1, client2, client3));

        Book book1 = Book.builder().isbn("1234").title("title1").author("author1").build();
        Book book2 = Book.builder().isbn("5678").title("title2").author("author2").build();
        Book book3 = Book.builder().isbn("0001").title("title3").author("author3").build();
        Book book4 = Book.builder().isbn("0002").title("title4").author("author4").build();

        bookRepository.saveAllAndFlush(List.of(book1, book2, book3, book4));

        ReadExperience readExperience1 = ReadExperience.builder().book(book1).client(client1).build();
        ReadExperience readExperience2 = ReadExperience.builder().book(book2).client(client1).build();
        ReadExperience readExperience3 = ReadExperience.builder().book(book3).client(client1).build();
        ReadExperience readExperience4 = ReadExperience.builder().book(book1).client(client2).build();
        ReadExperience readExperience5 = ReadExperience.builder().book(book2).client(client2).build();
        ReadExperience readExperience6 = ReadExperience.builder().book(book1).client(client3).build();
        ReadExperience readExperience7 = ReadExperience.builder().book(book3).client(client3).build();
        ReadExperience readExperience8 = ReadExperience.builder().book(book4).client(client3).build();

        readExperienceRepository.saveAllAndFlush(List.of(readExperience1, readExperience2, readExperience3, readExperience4,
                readExperience5, readExperience6, readExperience7, readExperience8));
    }

    @Test
    void 독서경험_업데이트() {
        //given
        Client client = clientRepository.findByLoginId("kw_lsh_3717").get();
        ClientDetails userDetails = new ClientDetails(client);

        List<String> bookIsbnList = List.of("1234", "5678", "0001", "0002");
        ReadExperienceUpdateRequestDto dto = new ReadExperienceUpdateRequestDto(bookIsbnList);
        List<ReadExperience> previousList = readExperienceRepository.findByLoginId("kw_lsh_3717");
        assertThat(previousList.size()).isEqualTo(3); // 기존의 독서경험의 수는 3개여야 함

        //when
        readExperienceService.updateReadExperience(dto, userDetails);
        em.clear();

        //then
        List<ReadExperience> updatedList = readExperienceRepository.findByLoginId("kw_lsh_3717");
        assertThat(updatedList.size()).isEqualTo(4); // 새로 업데이트 된 독서경험의 수는 4개여야 함
    }

    @Test
    void 독서경험_불러오기() {
        //given
        Client client = clientRepository.findByLoginId("kw_lsh_3717").get();
        ClientDetails userDetails = new ClientDetails(client);

        List<ReadExperience> previousList = readExperienceRepository.findByLoginId("kw_lsh_3717");
        assertThat(previousList.size()).isEqualTo(3); // 기존의 독서경험의 수는 3개여야 함

        PageRequest pageRequest = PageRequest.of(0,2);

        //when
        Page<ReadExperienceResponseDto> result = readExperienceService.getReadExperiences(userDetails, pageRequest);

        //then
        assertThat(result.getTotalPages()).isEqualTo(2); // 전체 페이지수는 2여야 함
        assertThat(result.getTotalElements()).isEqualTo(3); // 전체 개수는 3개여야 함
    }

    @Test
    void 독서경험_단일추가() {
        //given
        Client client = clientRepository.findByLoginId("kw_lsh_3717").get();
        ClientDetails userDetails = new ClientDetails(client);

        List<ReadExperience> previousList = readExperienceRepository.findByLoginId("kw_lsh_3717");
        assertThat(previousList.size()).isEqualTo(3); // 기존의 독서경험의 수는 3개여야 함

        ReadExperienceCreateDto dto = new ReadExperienceCreateDto("0002");

        //when
        readExperienceService.createReadExperience(dto, userDetails);
        em.clear();

        //then
        List<ReadExperience> updatedList = readExperienceRepository.findByLoginId("kw_lsh_3717");
        assertThat(updatedList.size()).isEqualTo(4); // 새로 업데이트 된 독서경험의 수는 4개여야 함
    }
}