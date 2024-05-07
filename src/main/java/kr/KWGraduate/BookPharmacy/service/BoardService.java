package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.answer.request.AnswerCreateDto;
import kr.KWGraduate.BookPharmacy.dto.board.request.BoardCreateDto;
import kr.KWGraduate.BookPharmacy.dto.board.request.BoardModifyDto;
import kr.KWGraduate.BookPharmacy.dto.board.response.BoardConcernPageDto;
import kr.KWGraduate.BookPharmacy.dto.board.response.BoardDetailDto;
import kr.KWGraduate.BookPharmacy.dto.board.response.BoardMyPageDto;
import kr.KWGraduate.BookPharmacy.dto.client.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import kr.KWGraduate.BookPharmacy.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
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

    public List<BoardConcernPageDto> getBoards(Pageable pageable){
        return boardRepository.findAllBoards(pageable).stream()
                .map(BoardConcernPageDto::new)
                .collect(Collectors.toList());
    }

    public BoardDetailDto getBoardDetail(Long boardId) throws Exception {
        BoardDetailDto boardDetailDto = boardRepository.findById(boardId)
                .map(BoardDetailDto::new)
                .orElseThrow(Exception::new);
        boardDetailDto.setAnswerBoardPageDto(answerService.getAnswers(boardId));
        return boardDetailDto;
    }
    public List<BoardConcernPageDto> getBoardsWithKeyword(Pageable pageable, String koreanKeyword){
        Keyword keyword = keywordBiMapService.getKeyword(koreanKeyword);
        return boardRepository.findByKeyword(pageable, keyword).stream()
                .map(BoardConcernPageDto::new)
                .collect(Collectors.toList());
    }
    public List<BoardConcernPageDto> getBoardsWithSearch(Pageable pageable , String searchKeyword){
        return boardRepository.findByTitleContainingOrDescriptionContaining(pageable, searchKeyword).stream()
                .map(BoardConcernPageDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long modifyBoard(Long boardId, BoardModifyDto boardModifyDto) throws Exception {
        Board board = boardRepository.findById(boardId).orElseThrow(Exception::new);
        Keyword keyword = keywordBiMapService.getKeyword(boardModifyDto.getKoreanKeyword());

        board.modifyBoard(boardModifyDto.getTitle() , boardModifyDto.getDescription(), keyword);
        answerService.updateAnswers(boardModifyDto.getAnswers());
        return boardId;
    }

    @Transactional
    public Long createBoard(BoardCreateDto boardCreateDto, AuthenticationAdapter authenticationAdapter){
        Client client = getClient(authenticationAdapter);
        Keyword keyword = keywordBiMapService.getKeyword(boardCreateDto.getKoreanKeyword());

        Board board = boardCreateDto.toEntity(client,keyword);
        Long id = boardRepository.save(board).getId();
        answerService.createAnswer(id,boardCreateDto.getAnswers());
        return id;
    }

    private Client getClient(AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();
        return clientRepository.findByLoginId(username).get();
    }

    @Transactional
    public void deleteBoard(Long boardId){
        boardRepository.deleteById(boardId);
    }

    public List<BoardMyPageDto> getMyBoards(Pageable pageable, AuthenticationAdapter authenticationAdapter){
        Slice<Board> boards = boardRepository.findByUsername(pageable, authenticationAdapter.getUsername());

        return getBoardMyPageDtos(boards);

    }

    private List<BoardMyPageDto> getBoardMyPageDtos(Slice<Board> boards) {
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

        return boards.stream()
                .map((board) -> {
                    Long count = map.getOrDefault(board.getId(),0L);
                    return new BoardMyPageDto(board, count);
                })
                .collect(Collectors.toList());
    }


}
