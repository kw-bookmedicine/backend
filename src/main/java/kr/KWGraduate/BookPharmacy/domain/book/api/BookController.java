package kr.KWGraduate.BookPharmacy.domain.book.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import kr.KWGraduate.BookPharmacy.domain.category.dto.response.CategoryDto;
import kr.KWGraduate.BookPharmacy.domain.book.service.BookService;
import kr.KWGraduate.BookPharmacy.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    @Operation(summary = "책 상세정보 출력", description = "id로 책 상세정보 조회하기(parameter로 id 입력")
    @GetMapping(value = "/detail")
    public ResponseEntity<BookDto> getBookDetails(@RequestParam(name ="id") Long id){
        BookDto result = bookService.getBookDetails(id);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "중분류에 해당하는 book 리스트 요청", description = "중분류에 해당하는 book들을 페이징해서 " +
            "요청 예) /api/book/list/middle?name=한국소설&page=0&size=5")
    @GetMapping(value = "/list/middle")
    public ResponseEntity<Page<BookSearchResponseDto>> getBookListByMiddleCategory(@RequestParam(name = "middleCategoryId") Long categoryId,
                                                                     @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("view_count").descending());
        Page<BookSearchResponseDto> result = bookService.getBookPageByMiddleCategory(categoryId, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "대분류에 속하는 중분류들에 대한 각각의 book 리스트 요청", description = "Map<중분류 이름, 책리스트>로 반환")
    @GetMapping(value = "/list/big")
    public ResponseEntity<Object> getListMapByBigCategory(@RequestParam(name = "bigCategoryId") Long bigCategoryId){

        List<Map<String, Object>> result = bookService.getBookListByBigCategoryChildren(bigCategoryId);

        return ResponseEntity.ok(result);
    }

}
