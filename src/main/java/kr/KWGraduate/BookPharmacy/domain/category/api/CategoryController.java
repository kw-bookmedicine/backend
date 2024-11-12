package kr.KWGraduate.BookPharmacy.domain.category.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "대분류 출력", description = "대분류에 해당하는 카테고리 불러오기, Map<대분류, List<중분류>> 형식으로 반환함")
    @GetMapping(value = "/big")
    public ResponseEntity<List<Map<String, Object>>> getCategoryGroupByBig(){
        List<Map<String, Object>> result = categoryService.getAllCategoryGrouped();
        return ResponseEntity.ok(result);
    }

//    미구현 (카테고리 넘버로     카테고리 바인딩 해야함)
//    @GetMapping(value = "/{category_number}")
//    public ResponseEntity<List<CategoryDto>> getChildCategory(@PathVariable("category_number") String categoryNumber) {
//
////        categoryNumber로 categoryName 찾는 함수 구현
////        List<CategoryDto> result = categoryService.getChildCategory(categoryName);
//
//        return ResponseEntity.ok(result);
//    }
}
