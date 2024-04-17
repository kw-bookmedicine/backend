package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.answer.request.AnswerCreateDto;
import kr.KWGraduate.BookPharmacy.dto.answer.request.AnswerUpdateDto;
import kr.KWGraduate.BookPharmacy.dto.answer.request.AnswerUpdateIndividualDto;
import kr.KWGraduate.BookPharmacy.dto.answer.response.AnswerBoardPageDto;
import kr.KWGraduate.BookPharmacy.entity.Answer;
import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.repository.AnswerRepository;
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

    @Transactional
    public void createAnswer(Board board, List<AnswerCreateDto> answerCreateDtos){
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

}
