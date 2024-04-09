package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.client.ClientDetails;
import kr.KWGraduate.BookPharmacy.dto.prescription.request.PrescriptionCreateDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.request.PrescriptionModifyDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.response.PrescriptionBoardPageDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.response.PrescriptionMyPageDto;
import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Prescription;
import kr.KWGraduate.BookPharmacy.enums.Keyword;
import kr.KWGraduate.BookPharmacy.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.PrescriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PrescriptionServiceTest {

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("createdDate"));

    Long boardId = 0L;
    @BeforeEach
    void beforeJob(){
        Client client = clientRepository.findByLoginId("sim").get();
        Book book1 = bookRepository.findById(1L).get();
        Book book2 = bookRepository.findById(2L).get();
        Book book3 = bookRepository.findById(3L).get();
        Book book4 = bookRepository.findById(4L).get();

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

        boardRepository.saveAll(List.of(board1,board2,board3,board4,board5,board6));

        boardId = board1.getId();

        List<Prescription> list = List.of(
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board1)
                        .client(client)
                        .book(book1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board2)
                        .client(client)
                        .book(book2)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board3)
                        .client(client)
                        .book(book3)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board4)
                        .client(client)
                        .book(book4)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board5)
                        .client(client)
                        .book(book1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board6)
                        .client(client)
                        .book(book2)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board1)
                        .client(client)
                        .book(book3)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board2)
                        .client(client)
                        .book(book4)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board3)
                        .client(client)
                        .book(book1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board4)
                        .client(client)
                        .book(book2)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board5)
                        .client(client)
                        .book(book3)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board6)
                        .client(client)
                        .book(book4)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board1)
                        .client(client)
                        .book(book1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board2)
                        .client(client)
                        .book(book2)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board3)
                        .client(client)
                        .book(book3)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board4)
                        .client(client)
                        .book(book4)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board5)
                        .client(client)
                        .book(book1)
                        .build(),
                Prescription.builder()
                        .title("잘가")
                        .description("이하정")
                        .board(board6)
                        .client(client)
                        .book(book2)
                        .build()
        );

        prescriptionRepository.saveAll(list);

    }


    @Test
    void 게시판_처방전_조회(){

        for (PrescriptionBoardPageDto prescription : prescriptionService.getPrescriptions(pageRequest,boardId)) {
            System.out.println(prescription);
        }

    }
    @Test
    void 내가_쓴_처빙전_생성(){
        Client client = clientRepository.findByLoginId("sim").get();

        ClientDetails userDetails = new ClientDetails(client);

        for (PrescriptionMyPageDto prescription : prescriptionService.getPrescriptions(pageRequest, userDetails)) {
            System.out.println(prescription);
        }

    }

    @Test
    void 생성_수정_삭제(){
        Client client = clientRepository.findByLoginId("sim").get();

        ClientDetails userDetails = new ClientDetails(client);

        PrescriptionCreateDto createDto = PrescriptionCreateDto.builder()
                .title("이하정")
                .description("보고싶어")
                .boardId(boardId)
                .bookId(1L)
                .build();
        Long createId = prescriptionService.createPrescription(createDto, userDetails);

        System.out.println(prescriptionRepository.findById(createId).get().toString());

        PrescriptionModifyDto modifyDto = PrescriptionModifyDto.builder()
                .title("수정")
                .description("이하정 생일 축하해")
                .bookId(2L)
                .build();

        Long modifyId = prescriptionService.modifyPrescription(createId, modifyDto);

        System.out.println(prescriptionRepository.findById(modifyId).get().toString());

        prescriptionService.deletePrescription(modifyId);

        org.assertj.core.api.Assertions.assertThat(prescriptionRepository.findById(createId).isEmpty()).isTrue();
    }

}