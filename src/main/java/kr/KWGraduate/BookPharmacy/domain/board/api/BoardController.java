package kr.KWGraduate.BookPharmacy.domain.board.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.board.dto.request.BoardCreateDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.request.BoardModifyDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardConcernPageDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardDetailDto;
import kr.KWGraduate.BookPharmacy.domain.board.dto.response.BoardMyPageDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
@Tag(name = "게시판 api")
public class BoardController {
    private final BoardService boardService;



    @GetMapping("/all")
    @Operation(summary = "모든 게시판 조회", description = "무한 스크롤을 위한 size와 page입력 필수")
    public ResponseEntity<List<BoardConcernPageDto>> getBoard(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdDate"));
        return ResponseEntity.ok(boardService.getBoards(pageRequest));
    }

    @GetMapping("/keyword")
    @Operation(summary = "게시판 키워드 별 조회", description = "무한 스크롤을 위한 size와 page입력 필수, 키워드 입력")

    public ResponseEntity<List<BoardConcernPageDto>> getBoard(
            @RequestParam(name = "keyword")Keyword keyword
            ,@RequestParam("page") int page
            ,@RequestParam("size") int size)
    {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdDate"));
        return ResponseEntity.ok(boardService.getBoards(pageRequest, keyword));
    }

    @GetMapping("/search")
    @Operation(summary = "검색으로 인한 게시판 조회", description = "무한 스크롤을 위한 size와 page입력")
    public ResponseEntity<List<BoardConcernPageDto>> getBoard(
            @RequestParam("searchKeyword") String searchKeyword,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdDate"));
        return ResponseEntity.ok(boardService.getBoards(pageRequest,searchKeyword));
    }
    @GetMapping("/{boardId}")
    @Operation(summary = "특정 게시판 상세조회", description = "boardId 입력")
    public ResponseEntity<BoardDetailDto> getBoard(@PathVariable("boardId") Long boardId) throws Exception {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId));
    }

    @PutMapping("/{boardId}")
    @Operation(summary = "게시판 수정", description = "boardId 입력")
    public ResponseEntity<String> modifyBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardModifyDto boardModifyDto) throws Exception {
        boardService.modifyBoard(boardId, boardModifyDto);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/new")
    @Operation(summary = "게시판 생성", description = "UserDetails는 쿠키정보로 사용자 정보를 가져옴")
    public ResponseEntity<String> createBoard(@AuthenticationPrincipal UserDetails userDetails, @RequestBody BoardCreateDto boardCreateDto){
        boardService.createBoard(boardCreateDto, (AuthenticationAdapter) userDetails);
        return ResponseEntity.ok("success");
    }

    @DeleteMapping("/{boardId}")
    @Operation(summary = "게시판 삭제")
    public ResponseEntity<String> deleteBoard(@PathVariable("boardId") Long boardId){
        boardService.deleteBoard(boardId);
        return ResponseEntity.ok("success");
    }
    @GetMapping("/my")
    @Operation(summary = "내 게시판 조회", description = "UserDetails는 쿠키정보로 사용자 정보를 가져옴")
    public ResponseEntity<List<BoardMyPageDto>> getBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by("createdDate"));

        return ResponseEntity.ok(boardService.getMyBoards(pageRequest, (AuthenticationAdapter) userDetails));
    }
}
