package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.CategoryDto;
import kr.KWGraduate.BookPharmacy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "대분류 출력", description = "대분류에 해당하는 카테고리 불러오기")
    @GetMapping(value = "/big")
    public ResponseEntity<List<CategoryDto>> getBigCategory(){

        List<CategoryDto> result = categoryService.getBigCategoryList();
        return ResponseEntity.ok(result);
    }

//    미구현 (카테고리 넘버로 카테고리 바인딩 해야함)
//    @GetMapping(value = "/{category_number}")
//    public ResponseEntity<List<CategoryDto>> getChildCategory(@PathVariable("category_number") String categoryNumber) {
//
////        categoryNumber로 categoryName 찾는 함수 구현
////        List<CategoryDto> result = categoryService.getChildCategory(categoryName);
//
//        return ResponseEntity.ok(result);
//    }
}
