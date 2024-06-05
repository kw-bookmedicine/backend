package kr.KWGraduate.BookPharmacy.domain.board.service;

import kr.KWGraduate.BookPharmacy.domain.answer.service.AnswerService;
import kr.KWGraduate.BookPharmacy.domain.board.dto.request.BoardCreateDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.request.BoardModifyDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardConcernPageDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardDetailDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardMyPageDto;
import kr.KWGraduate.BookPharmacy.domain.board.event.BoardUpdatedEvent;
import kr.KWGraduate.BookPharmacy.domain.keyword.service.KeywordBiMapService;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.response.OneLineResponseDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.board.domain.Board;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.prescription.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final ClientRepository clientRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final AnswerService answerService;
    private final KeywordBiMapService keywordBiMapService;
    private final ApplicationEventPublisher applicationEventPublisher;
    public Page<BoardConcernPageDto> getBoards(Pageable pageable){
        Page<Board> pageResult = boardRepository.findAllBoards(pageable);
        Page<BoardConcernPageDto> dtoList = pageResult.map(board -> new BoardConcernPageDto(board));

        return dtoList;
    }

    public BoardDetailDto getBoardDetail(Long boardId) throws Exception {
        BoardDetailDto boardDetailDto = boardRepository.findById(boardId)
                .map(BoardDetailDto::new)
                .orElseThrow(Exception::new);
        boardDetailDto.setAnswerBoardPageDto(answerService.getAnswers(boardId));
        return boardDetailDto;
    }
    public Page<BoardConcernPageDto> getBoards(Pageable pageable, Keyword keyword){
        Page<Board> pageResult = boardRepository.findByKeyword(pageable, keyword);
        Page<BoardConcernPageDto> dtoList = pageResult.map(board -> new BoardConcernPageDto(board));

        return dtoList;
    }
    public Page<BoardConcernPageDto> getBoards(Pageable pageable , String searchKeyword){
        Page<Board> pageResult = boardRepository.findByTitleContainingOrDescriptionContaining(pageable, searchKeyword);

        Page<BoardConcernPageDto> dtoList = pageResult.map(board -> new BoardConcernPageDto(board));

        return dtoList;
    }

    @Transactional
    public Long modifyBoard(Long boardId, BoardModifyDto boardModifyDto) throws Exception {
        Board board = boardRepository.findById(boardId).orElseThrow(Exception::new);

        Keyword originKeyword = board.getKeyword();
        String originDescription = board.getDescription();

        board.modifyBoard(boardModifyDto.getTitle() , boardModifyDto.getDescription(), boardModifyDto.getKeyword());
        answerService.updateAnswers(boardModifyDto.getAnswers());

        if(!(originDescription.equals(board.getDescription()) && originKeyword == board.getKeyword())){
            applicationEventPublisher.publishEvent(new BoardUpdatedEvent(this,boardId));
        }

        return boardId;
    }

    @Transactional
    public Long createBoard(BoardCreateDto boardCreateDto, AuthenticationAdapter authenticationAdapter){
        Client client = getClient(authenticationAdapter);
        Board board = boardCreateDto.toEntity(client);
        Long id = boardRepository.save(board).getId();
        answerService.createAnswer(id,boardCreateDto.getAnswers());
        client.plusBoardCount();

        applicationEventPublisher.publishEvent(new BoardUpdatedEvent(this,id));

        return id;
    }

    private Client getClient(AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();
        return clientRepository.findByLoginId(username).get();
    }

    @Transactional
    public void deleteBoard(Long boardId, AuthenticationAdapter authenticationAdapter){
        Client client = getClient(authenticationAdapter);
        client.minusBoardCount();
        boardRepository.deleteById(boardId);
    }

    public Page<BoardMyPageDto> getMyBoards(Pageable pageable, AuthenticationAdapter authenticationAdapter){
        Page<Board> boards = boardRepository.findByUsername(pageable, authenticationAdapter.getUsername());

        return getBoardMyPageDtos(boards);

    }

    private Page<BoardMyPageDto> getBoardMyPageDtos(Page<Board> boards) {
        List<Long> boardIds = new ArrayList<>();

        for(Board b : boards){
            boardIds.add(b.getId());
        }

        Map<Long,Long> map = new HashMap<>();

        for (Object[] objects : prescriptionRepository.countByBoard(boardIds)) {
            Long boardId = (Long)objects[0];
            Long count = (Long)objects[1];
            map.put(boardId , count);
        }

        return boards.map((board) -> {
                    Long count = map.getOrDefault(board.getId(),0L);
                    return new BoardMyPageDto(board, count);
                });
    }

}
