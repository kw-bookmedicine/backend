package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BoardRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BookRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.dto.repository.BoardBasedRecommendRepositoryDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.*;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RecommendRepositoryTest {


    @Autowired
    RecommendRepository recommendRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BookRecommendRepository bookRecommendRepository;
    @Autowired
    BoardRecommendRepository boardRecommendRepository;
    @Autowired
    ClientRecommendRepository clientRecommendRepository;
    @Autowired
    BoardRepository boardRepository;


    @Test
    @DisplayName("연관된 책 기반 추천 책 조회하기")
    @Rollback(value = false)
    public void findByBookbased(){
        Book recommendedBook = bookRepository.findById(1L).get();

        List<Book> books = LongStream.rangeClosed(2, 11)
                .boxed()
                .map(l -> bookRepository.findById(l).get())
                .collect(Collectors.toList());

        for (Book recommendingBook : books) {
            BookRecommend bookRecommend = BookRecommend.builder()
                    .recommendedBook(recommendedBook)
                    .recommendingBook(recommendingBook)
                    .build();
            bookRecommendRepository.save(bookRecommend);
        }

        assertThat(bookRecommendRepository.count()).isEqualTo(10);
        assertThat(recommendRepository.findByBookBasedRecommend(recommendedBook.getIsbn())).containsAll(books);

    }
    @Test
    @DisplayName("비슷한 유저가 읽은 책 추천 조회하기")
    @Rollback(value = false)
    public void findByClientbased(){
        Client client = clientRepository.findById(1L).get();
        System.out.println(client);

        List<Book> books = LongStream.rangeClosed(2, 14)
                .boxed()
                .map(l -> bookRepository.findById(l).get())
                .collect(Collectors.toList());

        List<Book> aiPrescription = new ArrayList<>(books.subList(0, 3));
        List<Book> clientBasedRecommend = new ArrayList<>(books.subList(3,13));

        int rank=0;
        for(Book bookRecommend : books) {
            ClientRecommend clientRecommend = ClientRecommend
                    .builder()
                    .client(client)
                    .book(bookRecommend)
                    .rank(rank++)
                    .build();

            clientRecommendRepository.save(clientRecommend);
        }



        assertThat(clientRecommendRepository.count()).isEqualTo(13);
        assertThat(recommendRepository.findByClientBasedRecommend(1L)).containsAll(clientBasedRecommend);
        assertThat(recommendRepository.findByClientAiPrescription(1L)).containsAll(aiPrescription);
    }

    @Test
    @DisplayName("고민게시판 책 추천 unique값 확인")
    public void isBoardUnique(){
        Board board1 = boardRepository.findById(173L).get();


        Book book1 = bookRepository.findById(1L).get();
        Book book2 = bookRepository.findById(2L).get();

        BoardRecommend boardRecommend1 = BoardRecommend
                .builder()
                .board(board1)
                .book(book1)
                .build();
        boardRecommendRepository.save(boardRecommend1);


        BoardRecommend boardRecommend2 = BoardRecommend
                .builder()
                .board(board1)
                .book(book2)
                .build();

        Assertions.assertThrowsExactly(DataIntegrityViolationException.class, () ->{
            boardRecommendRepository.save(boardRecommend2);
        });


    }

    @Test
    @DisplayName("고민 게시판 책 추천 조회")
    @Rollback(value = false)
    public void findBoardbased(){
        Board board1 = boardRepository.findById(167L).get();
        Board board2 = boardRepository.findById(168L).get();
        Board board3 = boardRepository.findById(169L).get();
        Board board4 = boardRepository.findById(170L).get();

        Book book1 = bookRepository.findById(1L).get();
        BoardBasedRecommendRepositoryDto recommendDto1 = new BoardBasedRecommendRepositoryDto(book1,"한국 영어 일본어");
        Book book2 = bookRepository.findById(2L).get();
        BoardBasedRecommendRepositoryDto recommendDto2 = new BoardBasedRecommendRepositoryDto(book2,"자바 연애 컴퓨터");

        BoardRecommend boardRecommend1 = BoardRecommend
                .builder()
                .board(board1)
                .book(book1)
                .keywords("한국 영어 일본어")
                .build();
        boardRecommendRepository.save(boardRecommend1);


        BoardRecommend boardRecommend2 = BoardRecommend
                .builder()
                .board(board2)
                .book(book2)
                .keywords("자바 연애 컴퓨터")
                .build();
        boardRecommendRepository.save(boardRecommend2);

        BoardRecommend boardRecommend3 = BoardRecommend
                .builder()
                .board(board3)
                .book(book1)
                .keywords("한국 영어 일본어")
                .build();
        boardRecommendRepository.save(boardRecommend3);

        BoardRecommend boardRecommend4 = BoardRecommend
                .builder()
                .board(board4)
                .book(book2)
                .keywords("자바 연애 컴퓨터")
                .build();
        boardRecommendRepository.save(boardRecommend4);

        assertThat(boardRecommendRepository.count()).isEqualTo(4);
        assertThat(recommendRepository.findByBoardBasedRecommend(167L).get()).isEqualTo(recommendDto1);
        assertThat(recommendRepository.findByBoardBasedRecommend(168L).get()).isEqualTo(recommendDto2);
        assertThat(recommendRepository.findByBoardBasedRecommend(169L).get()).isEqualTo(recommendDto1);
        assertThat(recommendRepository.findByBoardBasedRecommend(170L).get()).isEqualTo(recommendDto2);


    }

}
