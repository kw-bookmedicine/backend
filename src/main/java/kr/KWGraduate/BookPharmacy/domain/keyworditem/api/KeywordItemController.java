package kr.KWGraduate.BookPharmacy.domain.keyworditem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.service.KeywordItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keyword")
@Tag(name = "Keyword")
public class KeywordItemController {
    private final KeywordItemService keywordItemService;

    @GetMapping("/book")
    @Operation(summary = "책의 키워드 요청", description = "해당 책에 대한 키워드들을 요청 <br>" +
            "요청 예) /api/keyword/book?isbn=1234-5678-5")
    public ResponseEntity<List<KeywordItemDto>> getBookKeyword(@RequestParam(name = "isbn") String isbn) {
        return ResponseEntity.ok(keywordItemService.getBookKeywords(isbn));
    }

    @GetMapping("/client")
    @Operation(summary = "클라이언트의 키워드 요청", description = "해당 클라이언트에 대한 키워드들을 요청")
    public ResponseEntity<List<KeywordItemDto>> getClientKeyword(@RequestParam(name = "loginId") String loginId){
        return ResponseEntity.ok(keywordItemService.getClientKeywords(loginId));
    }

}
