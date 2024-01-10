package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.KWGraduate.BookPharmacy.dto.CategoryDto;
import kr.KWGraduate.BookPharmacy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "대분류 출력", description = "대분류에 해당하는 카테고리 불러오기")
    @GetMapping(value = "/big")
    public ResponseEntity<List<CategoryDto>> getBigCategories(){

        List<CategoryDto> result = categoryService.getBigCategoryList();
        return ResponseEntity.ok(result);
    }
}
