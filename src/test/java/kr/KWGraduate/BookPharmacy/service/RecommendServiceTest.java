package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BoardRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BookRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.dto.repository.BoardBasedRecommendRepositoryDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BoardBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.ClientBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BoardRecommendRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRecommendRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.ClientRecommendRepository;
import kr.KWGraduate.BookPharmacy.domain.book.service.RecommendService;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.global.security.auth.dto.ClientDetails;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@SpringBootTest
@Transactional
public class RecommendServiceTest {
    @Autowired
    private RecommendService recommendService;

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
    @DisplayName("client관련 추천 테스트")
    public void clientBasedRecommend(){

        Client client = clientRepository.findById(1L).get();
        System.out.println(client);

        List<Book> books = LongStream.rangeClosed(2, 14)
                .boxed()
                .map(l -> bookRepository.findById(l).get())
                .collect(Collectors.toList());

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

        ClientDetails userDetails = new ClientDetails(client);

        Assertions.assertThat(recommendService.getClientBasedAiPrescription(userDetails).size()).isEqualTo(3);
        Assertions.assertThat(recommendService.getClientBasedRecommend(userDetails).size()).isEqualTo(10);

    }
    @Test
    @DisplayName("book관련 추천 테스트")
    public void bookBasedRecommend(){

        Book recommendedBook = bookRepository.findById(1L).get();
        String isbn = recommendedBook.getIsbn();

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

        Assertions.assertThat(recommendService.getBookBasedRecommend(isbn).size()).isEqualTo(10);

    }
    @Test
    @DisplayName("board관련 추천 테스트")
    public void boardBasedRecommend(){
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

        BoardBasedRecommendDto dto1 = new BoardBasedRecommendDto(recommendDto1);
        BoardBasedRecommendDto dto2 = new BoardBasedRecommendDto(recommendDto2);

        Assertions.assertThat(recommendService.getBoardBasedRecommend(167L)).isEqualTo(dto1);
        Assertions.assertThat(recommendService.getBoardBasedRecommend(168L)).isEqualTo(dto2);
        Assertions.assertThat(recommendService.getBoardBasedRecommend(169L)).isEqualTo(dto1);
        Assertions.assertThat(recommendService.getBoardBasedRecommend(170L)).isEqualTo(dto2);

    }
}
