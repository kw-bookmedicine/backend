package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.prescription.domain.Prescription;
import kr.KWGraduate.BookPharmacy.domain.prescription.repository.PrescriptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@Transactional
class PrescriptionRepositoryTest {

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BoardRepository boardRepository;

    @Test
    void test1(){
        PageRequest pageRequest = PageRequest.of(0,3);
        for (Prescription prescription : prescriptionRepository.findByBoardId(pageRequest, 206L)) {
            System.out.println(prescription);
        }

        prescriptionRepository.deleteById(206L);
        Assertions.assertThat((int) prescriptionRepository.findByBoardId(pageRequest, 206L).stream().count()).isEqualTo(0);

    }

    @Test
    void test(){

        Client client = clientRepository.findById(1L).get();
        Book book = bookRepository.findById(1L).get();


        Board board1 = Board.builder()
                .title("마음치료")
                .client(client)
                .description("asdf")
                .keyword(Keyword.Economy_Management)
                .build();



        Board board2 = Board.builder()
                .title("게시판2")
                .client(client)
                .description("asdfㅁㄴㅇㄻㄴㅇㄹ")
                .keyword(Keyword.Economy_Management)
                .build();


        Board save = boardRepository.save(board1);
        Long boardId1 = save.getId();
        Board save1 = boardRepository.save(board2);
        Long boardId2 = save1.getId();


        Prescription prescription1 = Prescription.builder()
                .board(board1)
                .book(book)
                .client(client)
                .description("여기")
                .title("제목")
                .build();
        Prescription prescription2 = Prescription.builder()
                .board(board1)
                .book(book)
                .client(client)
                .description("너의 대한 나의 마음")
                .title("얼마나 큰지")
                .build();
        Prescription prescription3 = Prescription.builder()
                .board(board1)
                .book(book)
                .client(client)
                .description("이하정")
                .title("사랑해")
                .build();

        Prescription prescription4 = Prescription.builder()
                .board(board1)
                .book(book)
                .client(client)
                .description("우리")
                .title("행복하자")
                .build();
        Prescription prescription5 = Prescription.builder()
                .board(board1)
                .book(book)
                .client(client)
                .description("내가 잘할게")
                .title("근데 조금 마음이 아프네")
                .build();
        Prescription prescription6 = Prescription.builder()
                .board(board1)
                .book(book)
                .client(client)
                .description("이하정 너는")
                .title("나를 정말 사랑해?")
                .build();


        Prescription prescription7 = Prescription.builder()
                .board(board2)
                .book(book)
                .client(client)
                .description("여기")
                .title("제목")
                .build();
        Prescription prescription8 = Prescription.builder()
                .board(board2)
                .book(book)
                .client(client)
                .description("너의 대한 나의 마음")
                .title("얼마나 큰지")
                .build();
        Prescription prescription9 = Prescription.builder()
                .board(board2)
                .book(book)
                .client(client)
                .description("이하정")
                .title("사랑해")
                .build();

        Prescription prescription10 = Prescription.builder()
                .board(board2)
                .book(book)
                .client(client)
                .description("우리")
                .title("행복하자")
                .build();
        Prescription prescription11 = Prescription.builder()
                .board(board2)
                .book(book)
                .client(client)
                .description("내가 잘할게")
                .title("근데 조금 마음이 아프네")
                .build();
        Prescription prescription12 = Prescription.builder()
                .board(board2)
                .book(book)
                .client(client)
                .description("이하정 너는")
                .title("나를 정말 사랑해?")
                .build();



        prescriptionRepository.save(prescription1);
        prescriptionRepository.save(prescription2);
        prescriptionRepository.save(prescription3);
        prescriptionRepository.save(prescription4);
        prescriptionRepository.save(prescription5);
        prescriptionRepository.save(prescription6);

        prescriptionRepository.save(prescription7);
        prescriptionRepository.save(prescription8);
        prescriptionRepository.save(prescription9);
        prescriptionRepository.save(prescription10);
        prescriptionRepository.save(prescription11);
        prescriptionRepository.save(prescription12);



        PageRequest pageRequest = PageRequest.of(0,12, Sort.by("createdDate"));
//        for (Prescription prescription : prescriptionRepository.findByBoardId(pageRequest, 1L)) {
//            System.out.println(prescription);
//        }
//
//        for (Prescription prescription : prescriptionRepository.findByClientId(pageRequest,1L)) {
//            System.out.println(prescription);
//        }
//

        System.out.println("here");
        List<Object[]> objects = prescriptionRepository.countByBoard(List.of(boardId1, boardId2));

        for(var s : objects){
            System.out.println(s[0] +" "+ s[1]);
        }
    }

}