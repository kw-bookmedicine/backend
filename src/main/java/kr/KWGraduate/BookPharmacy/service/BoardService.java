package kr.KWGraduate.BookPharmacy.service;

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

    public List<BoardConcernPageDto> getBoards(Pageable pageable){
        return boardRepository.findAllBoards(pageable).stream()
                .map(BoardConcernPageDto::new)
                .collect(Collectors.toList());
    }

    public BoardDetailDto getBoardDetail(Long boardId) throws Exception {
        return boardRepository.findById(boardId)
                .map(BoardDetailDto::new)
                .orElseThrow(Exception::new);
    }
    public List<BoardConcernPageDto> getBoards(Pageable pageable, Keyword keyword){
        return boardRepository.findByKeyword(pageable, keyword).stream()
                .map(BoardConcernPageDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long modifyBoard(Long boardId, BoardModifyDto boardModifyDto) throws Exception {
        Board board = boardRepository.findById(boardId).orElseThrow(Exception::new);
        board.modifyBoard(boardModifyDto.getTitle() , boardModifyDto.getDescription(), boardModifyDto.getKeyword());
        return boardId;
    }

    @Transactional
    public Board createBoard(BoardCreateDto boardCreateDto, AuthenticationAdapter authenticationAdapter){
        Client client = getClient(authenticationAdapter);
        Board board = boardCreateDto.toEntity(client);

        return boardRepository.save(board);
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
