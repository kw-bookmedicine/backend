package kr.KWGraduate.BookPharmacy.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.FeedDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Categories;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.CategoryRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.FeedRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
@Rollback(false)
class FeedServiceTest {

    @Autowired FeedRepository feedRepository;
    @Autowired FeedService feedService;
    @Autowired ClientRepository clientRepository;
    @Autowired BookRepository bookRepository;
    @Autowired CategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void pagingTest(){

        //given
        Client cli1 = new Client("123","4321","ha", LocalDate.now(),"spqjf", "lsh@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client cli2 = new Client("124","4321","sim",LocalDate.now(),"sdfsdgs", "lsh2@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client cli3 = new Client("125","4321","lee",LocalDate.now(),"sdfsdgs", "lsh3@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        clientRepository.save(cli1);
        clientRepository.save(cli2);
        clientRepository.save(cli3);

        Categories category1 = Categories.builder().name("IT/컴퓨터").level(1).build();
        Categories category2 = Categories.builder().name("Java").parentCategory(category1).level(2).build();
        Categories category3 = Categories.builder().name("Python").parentCategory(category1).level(2).build();
        Categories category4 = Categories.builder().name("Docker").parentCategory(category1).level(2).build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        Book book1 = Book.builder().isbn("1234-5678-1").title("자바 ORM 표준 JPA 프로그래밍").author("김영한").publishYear("2022").bigCategory(category1).middleCategory(category2).build();
        Book book2 = Book.builder().isbn("1234-5678-2").title("도커 & 쿠버네티스").author("오가사와라 시게타카").publishYear("2022").bigCategory(category1).middleCategory(category4).build();
        Book book3 = Book.builder().isbn("1234-5678-3").title("Do it! 자바 프로그래밍 입문").author("박은종").publishYear("2022").bigCategory(category1).middleCategory(category2).build();
        Book book4 = Book.builder().isbn("1234-5678-4").title("Do it! 자바 코딩테스트").author("박은종").publishYear("2022").bigCategory(category1).middleCategory(category2).build();

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        Feed feed1 = new Feed(true, "good", 4.4f);
        feed1.setClientAndBook(cli1, book1);

        Feed feed2 = new Feed(true, "very good", 4.9f);
        feed2.setClientAndBook(cli2, book1);

        Feed feed3 = new Feed(true, "soso", 3.0f);
        feed3.setClientAndBook(cli1, book2);

        Feed feed4 = new Feed(true, "very good", 4.9f);
        feed4.setClientAndBook(cli3, book1);

        feedRepository.save(feed1);
        feedRepository.save(feed2);
        feedRepository.save(feed3);
        feedRepository.save(feed4);


        em.flush();
        em.clear();

        BookDto bookDto = new BookDto(book1);
        //when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<FeedDto> result = feedService.getFeedsPagingByBookDto(bookDto, pageRequest);

        for (FeedDto feedDto : result) {
            System.out.println("feedDto = " + feedDto);
        }
    }
}