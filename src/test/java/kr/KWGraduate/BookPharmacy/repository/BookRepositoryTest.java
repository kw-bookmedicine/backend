package kr.KWGraduate.BookPharmacy.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Categories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
@Rollback(false)
class BookRepositoryTest {
    @PersistenceContext EntityManager em;
    @Autowired BookRepository bookRepository;
    @Autowired CategoryRepository categoryRepository;

    @Test
    public void saveBook(){
        //given
        Categories category1 = Categories.builder().name("IT/컴퓨터").level(1).build();
        Categories category2 = Categories.builder().name("Java").parentCategory(category1).level(2).build();
        Categories category3 = Categories.builder().name("Python").parentCategory(category1).level(2).build();
        Categories category4 = Categories.builder().name("Docker").parentCategory(category1).level(2).build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);

        Book book1 = Book.builder().isbn("1234-5678-1").title("자바 ORM 표준 JPA 프로그래밍").author("김영한").bigCategory(category1).middleCategory(category2).build();
        Book book2 = Book.builder().isbn("1234-5678-2").title("도커 & 쿠버네티스").author("오가사와라 시게타카").bigCategory(category1).middleCategory(category4).build();
        Book book3 = Book.builder().isbn("1234-5678-3").title("Do it! 자바 프로그래밍 입문").author("박은종").bigCategory(category1).middleCategory(category2).build();
        Book book4 = Book.builder().isbn("1234-5678-4").title("Do it! 자바 코딩테스트").author("박은종").bigCategory(category1).middleCategory(category2).build();

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
        bookRepository.save(book4);

        em.flush();
        em.clear();

        //then
        Book savedBook1 = bookRepository.findOptionalByIsbn("1234-5678-1").get();

        Assertions.assertEquals(savedBook1.getIsbn(), book1.getIsbn());
    }

    @Test
    @DisplayName("대분류로 조회하기")
    public void findByBigCategory() {

        //then
        List<Book> bookListByCategory = bookRepository.findByBigCategory("IT/컴퓨터");

        for (Book book : bookListByCategory) {
            System.out.println("book = " + book);
        }
    }

    @Test
    @DisplayName("중분류로 조회하기")
    public void findByMiddleCategory() {

        //then
        List<Book> bookListByCategory = bookRepository.findByMiddleCategory("Java");

        for (Book book : bookListByCategory) {
            System.out.println("book = " + book);
        }
    }

    @Test
    @DisplayName("중분류로 조회하기(페이징)")
    public void findPagingByMiddleCategory() {

        //when
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Book> page = bookRepository.findBookPagingByMiddleCategory("Java", pageRequest);
        //then
        for (Book book : page.getContent()) {
            System.out.println("book = " + book);
        }
    }

    @Test
    @DisplayName("책 이름으로 조회하기")
    public void findByName() {
        List<Book> bookListByTitle = bookRepository.findByTitle("자바 ORM 표준 JPA 프로그래밍");

        for (Book book : bookListByTitle) {
            System.out.println("book = " + book);
        }
    }

    @Test
    @DisplayName("제목에 검색어를 포함하는 경우 조회하기")
    public void findBySearchKeyword() {
        List<Book> bookList = bookRepository.findByTitleContaining("자바");

        for (Book book : bookList) {
            System.out.println("book = " + book);
        }
    }

}