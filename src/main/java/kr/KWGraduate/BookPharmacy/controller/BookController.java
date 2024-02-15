package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.CategoryDto;
import kr.KWGraduate.BookPharmacy.service.BookService;
import kr.KWGraduate.BookPharmacy.service.CategoryService;
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

    @Operation(summary = "책 상세정보 출력", description = "isbn으로 책 상세정보 조회하기(parameter로 isbn 입력")
    @GetMapping(value = "/detail")
    public ResponseEntity<BookDto> getBookDetails(@RequestParam(name ="isbn") String isbn){
        BookDto result = bookService.getBookDetails(isbn);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "중분류에 해당하는 book 리스트 요청", description = "중분류에 해당하는 book들을 페이징해서 " +
            "요청 예) /api/feed/book?name=한국소설&page=0&size=5")
    @GetMapping(value = "/list/middle")
    public ResponseEntity<List<BookDto>> getBookListByMiddleCategory(@RequestParam(name = "name") String middleCategoryName,
                                                                     @Parameter(name = "page", description = "page는 기본 0부터 시작") Pageable pageable) {
        List<BookDto> result = bookService.getBookListByMiddleCategory(middleCategoryName, pageable);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "대분류에 속하는 중분류들에 대한 각각의 book 리스트 요청", description = "Map<중분류 이름, 책리스트>로 반환")
    @GetMapping(value = "/list/big")
    public ResponseEntity<Object> getListMapByBigCategory(@RequestParam(name = "name") String bigCategoryName){

        List<Map<String, Object>> result = new ArrayList<>();

        // 페이징 사이즈를 10으로 할당
        PageRequest pageRequest = PageRequest.of(0,10);


        // 대분류에 속하는 중분류들을 조회하고, 그 중분류들에 해당하는 책들 10권을 Map에 추가함
        List<CategoryDto> childCategories = categoryService.getChildCategory(bigCategoryName);
        for (CategoryDto childCategory : childCategories) {
            Map<String, Object> middleCategoryInfo = new HashMap<>();

            String categoryName = childCategory.getName();
            middleCategoryInfo.put("categoryName", categoryName);

            List<BookDto> bookDtoList = bookService.getBookListByMiddleCategory(categoryName, pageRequest);
            middleCategoryInfo.put("bookList", bookDtoList);

            result.add(middleCategoryInfo);
        }

        return ResponseEntity.ok(result);
    }

}
