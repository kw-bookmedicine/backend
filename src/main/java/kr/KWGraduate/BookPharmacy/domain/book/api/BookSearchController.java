package kr.KWGraduate.BookPharmacy.domain.book.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.book.dto.request.BookSearchDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/search/book")
@Tag(name="책 검색 Api")
public class BookSearchController {
    private final BookSearchService bookSearchService;

    @Operation(summary = "[모달]책제목에 검색어를 포함하는 book 리스트 6개 요청 <파라미터에서 target을 modal로 지정해야함>" +
            " 예시) /api/search/book?title=그림&target=modal")
    @GetMapping(params = {"title", "target=modal"})
    public ResponseEntity<List<BookSearchDto>> getBookListByTitleOnModal(@RequestParam(name = "title") String searchWord){

        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("count").descending());

        List<BookSearchDto> result = bookSearchService.searchBookOnModalByTitleContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "[모달]작가명에 검색어를 포함하는 book 리스트 6개 요청 <파라미터에서 target을 modal로 지정해야함>" +
            " 예시) /api/search/book?author=김&target=modal")
    @GetMapping(params = {"author", "target=modal"})
    public ResponseEntity<List<BookSearchDto>> getBookListByAuthorOnModal(@RequestParam(name = "author") String searchWord){

        PageRequest pageRequest = PageRequest.of(0, 6, Sort.by("count").descending());

        List<BookSearchDto> result = bookSearchService.searchBookOnModalByAuthorContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "[페이지]책제목에 검색어를 포함하는 book 리스트 ?개 요청 <파라미터에서 target을 page로 지정해야함>" +
            " 예시) /api/search/book?title=그림&target=page&page=0&size=20")
    @GetMapping(params = {"title", "target=page"})
    public ResponseEntity<Page<BookDto>> getBookListByTitleOnPage(@RequestParam(name = "title") String searchWord,
                                                                  @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("count").descending());

        Page<BookDto> bookDtoList = bookSearchService.searchBookOnPageByTitleContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(bookDtoList);
    }

    @Operation(summary = "[페이지]작가명에 검색어를 포함하는 book 리스트 ?개 요청 <파라미터에서 target을 page로 지정해야함>" +
            " 예시) /api/search/book?author=남해운&target=page&page=0&size=20")
    @GetMapping(params = {"author", "target=page"})
    public ResponseEntity<Page<BookDto>> getBookListByAuthorOnPage(@RequestParam(name = "author") String searchWord,
                                                                   @RequestParam(name = "page") int page, @RequestParam(name = "size") int size){

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("count").descending());

        Page<BookDto> bookDtoList = bookSearchService.searchBookOnPageByAuthorContainingSearchWord(searchWord, pageRequest);

        return ResponseEntity.ok(bookDtoList);
    }
}
