package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerCreateDto;
import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerUpdateIndividualDto;
import kr.KWGraduate.BookPharmacy.domain.answer.dto.response.AnswerBoardPageDto;
import kr.KWGraduate.BookPharmacy.domain.answer.service.AnswerService;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerServiceTest {
    @Autowired
    AnswerService answerService;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    ClientRepository clientRepository;

    Client client;
    Board board;

    @BeforeEach
    void task(){
        client = clientRepository.findByLoginId("sim").get();
        board = Board.builder()
                .title("자바")
                .client(client)
                .description("나를 잡아")
                .keyword(Keyword.Health)
                .build();
        boardRepository.save(board);

    }

    @Test
    void test(){

        List<AnswerCreateDto> createDtos = List.of(
                new AnswerCreateDto(Keyword.Health.getQuesAndDis().get(0).getQuestion() , "답변1"),
                new AnswerCreateDto(Keyword.Health.getQuesAndDis().get(1).getQuestion() , "답변2")
        );
        answerService.createAnswer(board.getId(), createDtos);

        List<AnswerBoardPageDto> answers = answerService.getAnswers(board.getId());

        Assertions.assertThat(2).isEqualTo(answers.size());

        List<Long> ids = List.of(answers.get(0).getId() , answers.get(1).getId());


        List<AnswerUpdateIndividualDto> list = new ArrayList<>();

        for(Long id : ids){
            AnswerUpdateIndividualDto dto = new AnswerUpdateIndividualDto(id, "답변"  + "asd" + id);
            list.add(dto);
        }

        AnswerUpdateDto dto = new AnswerUpdateDto(board.getId(),list);

        answerService.updateAnswers(dto);


        for (AnswerBoardPageDto answer : answerService.getAnswers(board.getId())) {
            Assertions.assertThat("답변asd"+answer.getId()).isEqualTo(answer.getAnswer());
        }


    }

}