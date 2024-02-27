package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.BookSearchDto;
import kr.KWGraduate.BookPharmacy.dto.KeywordItemDto;
import kr.KWGraduate.BookPharmacy.service.BookService;
import kr.KWGraduate.BookPharmacy.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final BookService bookService;

    @Operation(summary = "[모달]책제목에 검색어를 포함하는 book 리스트 6개 요청 <파라미터에서 target을 modal로 지정해야함>" +
            " 예시) /api/search/book?title=그림&target=modal")
    @GetMapping(value = "/book", params = {"title", "target=modal"})
    public ResponseEntity<List<BookSearchDto>> getBookListByTitleOnModal(@RequestParam(name = "title") String searchWord){

        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("count").descending());

        List<BookSearchDto> result = searchService.searchBookOnModalByTitleContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "[모달]작가명에 검색어를 포함하는 book 리스트 6개 요청 <파라미터에서 target을 modal로 지정해야함>" +
            " 예시) /api/search/book?author=김&target=modal")
    @GetMapping(value = "/book", params = {"author", "target=modal"})
    public ResponseEntity<List<BookSearchDto>> getBookListByAuthorOnModal(@RequestParam(name = "author") String searchWord){

        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("count").descending());

        List<BookSearchDto> result = searchService.searchBookOnModalByAuthorContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "[페이지]책제목에 검색어를 포함하는 book 리스트 ?개 요청 <파라미터에서 target을 page로 지정해야함>" +
            " 예시) /api/search/book?title=그림&target=page&page=0&size=20")
    @GetMapping(value = "/book", params = {"title", "target=page"})
    public ResponseEntity<List<BookDto>> getBookListByTitleOnPage(@RequestParam(name = "title") String searchWord,
                                                                  @Parameter(name = "page", description = "page는 기본 0부터 시작") Pageable pageable){

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.by("count").descending());

        List<BookDto> bookDtoList = searchService.searchBookOnPageByTitleContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(bookDtoList);
    }

    @Operation(summary = "[페이지]작가명에 검색어를 포함하는 book 리스트 ?개 요청 <파라미터에서 target을 page로 지정해야함>" +
            " 예시) /api/search/book?author=남해운&target=page&page=0&size=20")
    @GetMapping(value = "/book", params = {"author", "target=page"})
    public ResponseEntity<List<BookDto>> getBookListByAuthorOnPage(@RequestParam(name = "author") String searchWord,
                                                                  @Parameter(name = "page", description = "page는 기본 0부터 시작") Pageable pageable){

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),Sort.by("count").descending());

        List<BookDto> bookDtoList = searchService.searchBookOnPageByAuthorContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(bookDtoList);
    }

    @Operation(summary = "검색어를 포함하는 keyword 리스트 6개 요청 <파라미터에서 target을 modal로 지정해야함>"+
            " 예시) /api/search/keyword?name=기&target=modal")
    @GetMapping(value = "/keyword", params = {"name", "target=modal"})
    public ResponseEntity<List<KeywordItemDto>> getKeywordList(@RequestParam(name = "name") String searchWord){

        PageRequest pageRequest = PageRequest.of(0,6,Sort.by("count").descending());

        List<KeywordItemDto> result = searchService.searchKeywordBySearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }
}
