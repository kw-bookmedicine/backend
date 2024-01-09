package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.CategoryDto;
import kr.KWGraduate.BookPharmacy.entity.Categories;
import kr.KWGraduate.BookPharmacy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getBigCategoryList(){
        List<Categories> bigCategoryList = categoryRepository.findBigCategory();
        List<CategoryDto> categoryDtoList = CategoryDto.toDtoList(bigCategoryList);

        return categoryDtoList;
    }

    public List<CategoryDto> getChildCategory(String categoryName) {
        List<Categories> childCategoryList = categoryRepository.findChildrenByBigCategoryName(categoryName);
        List<CategoryDto> categoryDtoList = CategoryDto.toDtoList(childCategoryList);

        return categoryDtoList;
    }
}
