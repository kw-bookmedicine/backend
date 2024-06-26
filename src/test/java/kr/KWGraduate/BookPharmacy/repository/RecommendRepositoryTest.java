package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BoardRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BookRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
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

        List<BookRecommend> brList = new ArrayList<>();
        for (Book recommendingBook : books) {
            BookRecommend bookRecommend = BookRecommend.builder()
                    .recommendedBook(recommendedBook)
                    .recommendingBook(recommendingBook)
                    .build();
            brList.add(bookRecommendRepository.save(bookRecommend));
        }

        assertThat(bookRecommendRepository.count()).isEqualTo(10);
        assertThat(bookRecommendRepository.findByBookBasedRecommend(recommendedBook.getIsbn())).containsAll(brList);

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

        List<ClientRecommend> aiPrescription = new ArrayList<>();
        List<ClientRecommend> clientBasedRecommend = new ArrayList<>();

        int rank=0;
        for(Book bookRecommend : books) {
            ClientRecommend clientRecommend = ClientRecommend
                    .builder()
                    .client(client)
                    .book(bookRecommend)
                    .rank(rank++)
                    .build();

            if(rank < 4){
                aiPrescription.add(clientRecommendRepository.save(clientRecommend));
            }else{
                clientBasedRecommend.add(clientRecommendRepository.save(clientRecommend));
            }
        }



        assertThat(clientRecommendRepository.count()).isEqualTo(13);
        assertThat(clientRecommendRepository.findByClientBasedRecommend(1L)).containsAll(clientBasedRecommend);
        assertThat(clientRecommendRepository.findByClientAiPrescription(1L)).containsAll(aiPrescription);
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
        Book book2 = bookRepository.findById(2L).get();

        BoardRecommend boardRecommend1 = BoardRecommend
                .builder()
                .board(board1)
                .book(book1)
                .keywords("한국 영어 일본어")
                .build();
        BoardRecommend save1 = boardRecommendRepository.save(boardRecommend1);


        BoardRecommend boardRecommend2 = BoardRecommend
                .builder()
                .board(board2)
                .book(book2)
                .keywords("자바 연애 컴퓨터")
                .build();
        BoardRecommend save2 = boardRecommendRepository.save(boardRecommend2);

        BoardRecommend boardRecommend3 = BoardRecommend
                .builder()
                .board(board3)
                .book(book1)
                .keywords("한국 영어 일본어")
                .build();
        BoardRecommend save3 = boardRecommendRepository.save(boardRecommend3);

        BoardRecommend boardRecommend4 = BoardRecommend
                .builder()
                .board(board4)
                .book(book2)
                .keywords("자바 연애 컴퓨터")
                .build();
        BoardRecommend save4 = boardRecommendRepository.save(boardRecommend4);

        assertThat(boardRecommendRepository.count()).isEqualTo(4);
        assertThat(boardRecommendRepository.findByBoardBasedRecommend(167L).get()).isEqualTo(save1);
        assertThat(boardRecommendRepository.findByBoardBasedRecommend(168L).get()).isEqualTo(save2);
        assertThat(boardRecommendRepository.findByBoardBasedRecommend(169L).get()).isEqualTo(save3);
        assertThat(boardRecommendRepository.findByBoardBasedRecommend(170L).get()).isEqualTo(save4);


    }

}
