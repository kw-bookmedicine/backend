package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.enums.Keyword;
import kr.KWGraduate.BookPharmacy.enums.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Test
//    @Rollback(value = false)
    void test(){

//        Client client = Client.builder()
//                .email("sd")
//                .password("sdfsd")
//                .gender(Client.Gender.F)
//                .loginId("sdfsd")
//                .occupation(Client.Occupation.UNEMPLOYED)
//                .role("USER")
//                .nickname("dskfhsd")
//                .name("dsfsd")
//                .build();
//        clientRepository.save(client);

        Client client = clientRepository.findByLoginId("sim").get();

        Board board1 = Board.builder()
                .title("마음치료")
                .client(client)
                .description("asdf")
                .keyword(Keyword.Economy)
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
                .keyword(Keyword.Economy)
                .build();

        Board board6 = Board.builder()
                .title("마음이 너무 아파")
                .client(client)
                .description("이하정 짜증나")
                .keyword(Keyword.Relationships_Communication)
                .build();

        Board board7 = Board.builder()
                .title("마음 치료")
                .client(client)
                .description("도와줄게")
                .keyword(Keyword.Economy)
                .build();
        board7.setStatus(Status.PRESCRIBED);


        Board board8 = Board.builder()
                .title("자바")
                .client(client)
                .description("나를 잡아")
                .keyword(Keyword.Health)
                .build();
        board8.setStatus(Status.PRESCRIBED);

        Board board9 = Board.builder()
                .title("이별의 극복")
                .client(client)
                .description("나도 많이 아팠어")
                .keyword(Keyword.Relationships_Communication)
                .build();
        board9.setStatus(Status.PRESCRIBED);

        Board board10 = Board.builder()
                .title("여자친그ㅜ")
                .client(client)
                .description("....")
                .keyword(Keyword.Relationships_Communication)
                .build();
        board10.setStatus(Status.PRESCRIBING);

        Board board11 = Board.builder()
                .title("마음 치료")
                .client(client)
                .description("도와줄게")
                .keyword(Keyword.Economy)
                .build();
        board11.setStatus(Status.PRESCRIBED);

        Board board12 = Board.builder()
                .title("마음이 너무 아파")
                .client(client)
                .description("이하정 짜증나")
                .keyword(Keyword.Relationships_Communication)
                .build();
        board12.setStatus(Status.PRESCRIBED);

        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);
        boardRepository.save(board5);
        boardRepository.save(board6);
        boardRepository.save(board7);
        boardRepository.save(board8);
        boardRepository.save(board9);
        boardRepository.save(board10);
        boardRepository.save(board11);
        boardRepository.save(board12);

        PageRequest pageRequest = PageRequest.of(0,12, Sort.by("createdDate"));
        for (Board allBoard : boardRepository.findAllBoards(pageRequest)) {
            System.out.println(allBoard);
        }

        for (Board board : boardRepository.findByUsernameAndStatus(pageRequest, "sim", Status.PRESCRIBING)) {
            System.out.println(board);
        }

        for (Board board : boardRepository.findByUsername(pageRequest, "sim")) {
            System.out.println(board);
        }
        for (Board board : boardRepository.findByTitleContainingOrDescriptionContaining(pageRequest,"마음")) {
            System.out.println(board);
        }

        for (Board board : boardRepository.findByKeyword(pageRequest, Keyword.Economy)) {
            System.out.println(board);
        }


    }
}