package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.domain.answer.domain.Answer;
import kr.KWGraduate.BookPharmacy.domain.answer.repository.AnswerRepository;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class AnswerRepositoryTest {
    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ClientRepository clientRepository;
    Long boardId = -1L;
    @BeforeEach
    void insert(){
        Client client = clientRepository.findByLoginId("sim").get();

        Board board1 = Board.builder()
                .title("마음치료")
                .client(client)
                .description("asdf")
                .keyword(Keyword.Economy_Management)
                .build();

        Board board2 = Board.builder()
                .title("자바")
                .client(client)
                .description("나를 잡아")
                .keyword(Keyword.Health)
                .build();

        Board board3 = Board.builder()
                .title("이별의 극복")
                .client(client)
                .description("나도 많이 아팠어")
                .keyword(Keyword.Relationships_Communication)
                .build();

        Board board4 = Board.builder()
                .title("여자친그ㅜ")
                .client(client)
                .description("....")
                .keyword(Keyword.Relationships_Communication)
                .build();

        Board board5 = Board.builder()
                .title("마음 치료")
                .client(client)
                .description("도와줄게")
                .keyword(Keyword.Economy_Management)
                .build();

        Board board6 = Board.builder()
                .title("마음이 너무 아파")
                .client(client)
                .description("이하정 짜증나")
                .keyword(Keyword.Relationships_Communication)
                .build();

        boardRepository.saveAll(List.of(board1,board2,board3,board4,board5,board6));

        boardId = board1.getId();

        Answer answer1 = Answer.builder()
                .board(board1)
                .question(board1.getKeyword().getQuesAndDis().get(0).getQuestion())
                .answer("경제 기초 용어나 개념을 잘 모른다")
                .build();
        Answer answer2 = Answer.builder()
                .board(board1)
                .question(board1.getKeyword().getQuesAndDis().get(1).getQuestion())
                .answer("경제상식")
                .build();

        answerRepository.save(answer1);
        answerRepository.save(answer2);

    }

    @Test
    void test(){
        for (Answer answer : answerRepository.findByBoardId(boardId)) {
            System.out.println(answer);
        }

    }

    @Test
    void test2(){
        for (Answer answer : answerRepository.findByKeyword(Keyword.Economy_Management)) {
            System.out.println(answer);
        }

    }

    @Test
    void test3(){
        for (Answer answer : answerRepository.findByBoardId(171L)) {
            System.out.println(answer);
        }


        answerRepository.deleteById(171L);
        Assertions.assertThat(answerRepository.findByBoardId(171L).size()).isEqualTo(0);


    }
}