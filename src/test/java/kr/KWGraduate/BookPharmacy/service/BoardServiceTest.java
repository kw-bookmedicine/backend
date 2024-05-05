package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.board.request.BoardCreateDto;
import kr.KWGraduate.BookPharmacy.dto.board.request.BoardModifyDto;
import kr.KWGraduate.BookPharmacy.dto.board.response.BoardConcernPageDto;
import kr.KWGraduate.BookPharmacy.dto.board.response.BoardDetailDto;
import kr.KWGraduate.BookPharmacy.dto.board.response.BoardMyPageDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientDetails;
import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Prescription;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import kr.KWGraduate.BookPharmacy.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.PrescriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;



    PageRequest pageRequest = PageRequest.of(0,8, Sort.by("createdDate"));


    @BeforeEach
    public void setup(){
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

        Board board7 = Board.builder()
                .title("마음 치료")
                .client(client)
                .description("도와줄게")
                .keyword(Keyword.Economy_Management)
                .build();


        Board board8 = Board.builder()
                .title("자바")
                .client(client)
                .description("나를 잡아")
                .keyword(Keyword.Health)
                .build();

        Board board9 = Board.builder()
                .title("이별의 극복")
                .client(client)
                .description("나도 많이 아팠어")
                .keyword(Keyword.Relationships_Communication)
                .build();

        Board board10 = Board.builder()
                .title("여자친그ㅜ")
                .client(client)
                .description("....")
                .keyword(Keyword.Relationships_Communication)
                .build();

        Board board11 = Board.builder()
                .title("마음 치료")
                .client(client)
                .description("도와줄게")
                .keyword(Keyword.Economy_Management)
                .build();

        Board board12 = Board.builder()
                .title("마음이 너무 아파")
                .client(client)
                .description("이하정 짜증나")
                .keyword(Keyword.Relationships_Communication)
                .build();

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

        List<Prescription> list = List.of(
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board2)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board3)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board4)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board5)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board6)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board7)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board8)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board9)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board2)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board3)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board4)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board5)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board6)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board7)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board8)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board9)
                        .build()
        );

        prescriptionRepository.saveAll(list);

    }
    @Test
    void newTest(){
        for (Board board : boardRepository.findByUsername(pageRequest, "sim")) {
            for (var s : board.getKeyword().getQuesAndDis()) {
                System.out.println(s.getQuestion());
                System.out.println(s.getDistractor());
            }
        }

    }


    @Test
    void test(){
        for (BoardConcernPageDto board : boardService.getBoards(pageRequest)) {
            System.out.println(board);
        }

    }

    @Test
    void test2(){
        for (BoardConcernPageDto board : boardService.getBoards(pageRequest, Keyword.Economy_Management)) {
            System.out.println(board);
        }

    }

    @Test
    void test3() throws Exception {
        BoardDetailDto boardDetail = boardService.getBoardDetail(125L);
        System.out.println(boardDetail);
    }

    @Test
    void 내_게시판_조회(){

        Client client = clientRepository.findByLoginId("sim").get();

        ClientDetails userDetails = new ClientDetails(client);

        for (BoardMyPageDto board : boardService.getMyBoards(pageRequest, userDetails)) {
            System.out.println(board);
        }

    }

    @Test
    void 상태별_내_게시판_조회(){

        Client client = clientRepository.findByLoginId("sim").get();

        ClientDetails userDetails = new ClientDetails(client);

        for (BoardMyPageDto board : boardService.getMyBoards(pageRequest, userDetails)) {
            System.out.println(board);
        }
    }

    @Test
    void 게시판_생성_수정_삭제() throws Exception {

        Client client = clientRepository.findByLoginId("sim").get();

        ClientDetails userDetails = new ClientDetails(client);

        BoardCreateDto board = BoardCreateDto.builder()
                .title("게시판")
                .description("설명")
                .keyword(Keyword.Economy_Management)
                .build();

        Long boardId = boardService.createBoard(board, userDetails);

        System.out.println(boardService.getBoardDetail(boardId));

        BoardModifyDto modifyDto = BoardModifyDto.builder()
                .title("수정")
                .description("이하정 수정")
                .keyword(Keyword.Employment_Career)
                .build();
        Long boardId2 = boardService.modifyBoard(boardId, modifyDto);

        System.out.println(boardService.getBoardDetail(boardId2));

        boardService.deleteBoard(boardId);


    }

}