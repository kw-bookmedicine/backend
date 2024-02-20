package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.BookSearchDto;
import kr.KWGraduate.BookPharmacy.dto.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
@Tag(name="Search")
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "제목이나 작가에 검색어를 포함하는 book 리스트 6개 요청")
    @GetMapping(value = "/book")
    public ResponseEntity<List<BookSearchDto>> getBookListByTitleOrAuthorOnModal(@RequestParam(name = "searchWord") String searchWord){

        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("count").descending());

        List<BookSearchDto> result = searchService.searchBookBySearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "이름에 검색어를 포함하는 keyword 리스트 6개 요청")
    @GetMapping(value = "/keyword")
    public ResponseEntity<List<KeywordItemDto>> getKeywordList(@RequestParam(name = "searchWord") String searchWord){

        PageRequest pageRequest = PageRequest.of(0,6,Sort.by("count").descending());

        List<KeywordItemDto> result = searchService.searchKeywordBySearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }
}
