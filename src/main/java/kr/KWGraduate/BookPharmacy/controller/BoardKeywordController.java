package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.boardkeyword.BoardQuestionAndDistractorDto;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import kr.KWGraduate.BookPharmacy.service.BoardKeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Tag(name = "고민 게시판 및 한줄 처방 키워드")
@RequestMapping("/api/boardKeyword")
public class BoardKeywordController {
    private final BoardKeywordService boardKeywordService;

    @GetMapping("/keyword")
    @Operation(summary = "키워드 조회" )
    public ResponseEntity<List<String>> getKeywords(){
        return ResponseEntity.ok(boardKeywordService.getBoardKeywords());
    }

    @GetMapping("/distractor/{keyword}")
    @Operation(summary = "키워드에 따른 질문 조회",description = "키워드가 인자로 들어감")
    public ResponseEntity<List<BoardQuestionAndDistractorDto>> getDistractors(@PathVariable("keyword") Keyword keyword){
        return ResponseEntity.ok(boardKeywordService.getQuestionAndDistractor(keyword));
    }
}
