package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.readexperience.domain.ReadExperience;
import kr.KWGraduate.BookPharmacy.domain.readexperience.repository.ReadExperienceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ReadExperienceRepositoryTest {

    @Autowired ClientRepository clientRepository;
    @Autowired BookRepository bookRepository;
    @Autowired ReadExperienceRepository readExperienceRepository;

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
    void 유저의_아이디로_조회하기() {
        List<ReadExperience> result = readExperienceRepository.findByLoginId("kw_lsh_3717");
        assertThat(3).isEqualTo(result.size());
        assertThat(result.stream().map(exp -> exp.getBook().getTitle()).collect(Collectors.toList())).contains("title1", "title2", "title3");
    }

    @Test
    void 책의_isbn으로_조회하기() {
        List<ReadExperience> result = readExperienceRepository.findByBookIsbn("1234");
        assertThat(3).isEqualTo(result.size());
        assertThat(result.stream().map(exp -> exp.getClient().getLoginId()).collect(Collectors.toList())).contains("kw_lsh_3717", "kw_lsj_3717", "kw_sjy_3717");
    }

    @Test
    void 유저의_아이디와_책의_isbn으로_조회하기() {
        Client client = Client.builder().loginId("kw_test_3717").password("kw_test_password").nickname("KW_TEST").build();
        Book book = Book.builder().isbn("5555").title("title5").author("author5").build();
        ReadExperience readExperience = ReadExperience.builder().client(client).book(book).build();

        bookRepository.saveAndFlush(book);
        clientRepository.saveAndFlush(client);

        ReadExperience save = readExperienceRepository.saveAndFlush(readExperience);

        ReadExperience result = readExperienceRepository.findByLoginIdAndBookIsbn(client.getLoginId(), book.getIsbn()).get();

        assertThat(save.getId()).isEqualTo(result.getId());
    }
}