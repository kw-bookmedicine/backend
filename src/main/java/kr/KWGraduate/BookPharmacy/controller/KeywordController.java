package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.keyword.BookKeywordDTO;
import kr.KWGraduate.BookPharmacy.dto.keyword.ClientKeywordDTO;
import kr.KWGraduate.BookPharmacy.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keyword")
@Tag(name = "Keyword")
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/book")
    @Operation(summary = "책의 키워드 요청", description = "해당 책에 대한 키워드들을 요청 <br>" +
            "요청 예) /api/feed/book?isbn=123<br> " +
            "하지만 현재 우리 db에 예시 데이터만 존재하는데 isbn이 null값이 대다수임")
    public ResponseEntity<BookKeywordDTO> getBookKeyword(@RequestParam(name = "isbn") String isbn) {
        return ResponseEntity.ok(keywordService.getBookKeywords(isbn));
    }

    @GetMapping("/client")
    @Operation(summary = "클라이언트의 키워드 요청", description = "해당 클라이언트에 대한 키워드들을 요청 <br>" +
            "요청 예) /api/feed/client?id=1<br> " +
            "하지만 현재 우리 db에 예시 데이터만 존재하는데 id가 1~10까지 존재함")
    public ResponseEntity<ClientKeywordDTO> getClientKeyword(@RequestParam(name = "id") Long id){
        return ResponseEntity.ok(keywordService.getClientKeywords(id));
    }

}
