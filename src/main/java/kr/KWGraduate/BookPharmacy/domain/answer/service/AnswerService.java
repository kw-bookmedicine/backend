package kr.KWGraduate.BookPharmacy.domain.answer.service;

import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerCreateDto;
import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.answer.dto.request.AnswerUpdateIndividualDto;
import kr.KWGraduate.BookPharmacy.domain.answer.dto.response.AnswerBoardPageDto;
import kr.KWGraduate.BookPharmacy.domain.answer.domain.Answer;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.answer.repository.AnswerRepository;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void createAnswer(Long boardId, List<AnswerCreateDto> answerCreateDtos){
        Board board = boardRepository.findById(boardId).get();

        List<Answer> answers = answerCreateDtos.stream()
                .map(answerCreateDto -> answerCreateDto.toEntity(board))
                .collect(Collectors.toList());

        answerRepository.saveAll(answers);
    }

    @Transactional
    public void updateAnswers(AnswerUpdateDto answerUpdateDto){
        Long boardId = answerUpdateDto.getBoardId();

        List<Answer> answers = answerRepository.findByBoardId(boardId);

        List<AnswerUpdateIndividualDto> updateAnswers = answerUpdateDto.getAnswers();

        Map<Long, Answer> map = answers.stream()
                .collect(Collectors.toMap(Answer::getId, Function.identity()));

        updateAnswers.forEach(
                updateAnswer ->{
                    Answer answer = map.get(updateAnswer.getId());
                    answer.update(updateAnswer.getAnswer());
                }
        );
    }

    public List<AnswerBoardPageDto> getAnswers(Long boardId){
        return answerRepository.findByBoardId(boardId).stream()
                .map(AnswerBoardPageDto::new)
                .collect(Collectors.toList());
    }
    public void deleteAnswers(Long boardId){
        answerRepository.deleteByBoardId(boardId);
    }

}
