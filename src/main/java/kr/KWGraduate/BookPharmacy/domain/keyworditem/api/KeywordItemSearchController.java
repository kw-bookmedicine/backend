package kr.KWGraduate.BookPharmacy.domain.keyworditem.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.service.KeywordItemSearchService;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.dto.response.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search/keyword")
@Tag(name="키워드 검색 Api")
public class KeywordItemSearchController {

    private final KeywordItemSearchService keywordItemSearchService;
    private final BookService bookService;

    @Operation(summary = "검색어를 포함하는 keyword 리스트 6개 요청 <파라미터에서 target을 modal로 지정해야함>"+
            " 예시) /api/search/keyword?name=기&target=modal")
    @GetMapping(value = "/keyword", params = {"name", "target=modal"})
    public ResponseEntity<List<KeywordItemDto>> getKeywordList(@RequestParam(name = "name") String searchWord){

        PageRequest pageRequest = PageRequest.of(0,6,Sort.by("count").descending());

        List<KeywordItemDto> result = keywordItemSearchService.searchKeywordBySearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }
}
