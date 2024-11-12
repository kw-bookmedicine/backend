package kr.KWGraduate.BookPharmacy.domain.category.service;

import kr.KWGraduate.BookPharmacy.domain.category.dto.response.CategoryDto;
import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import kr.KWGraduate.BookPharmacy.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getBigCategoryList(){
        List<Categories> bigCategoryList = categoryRepository.findBigCategory();
        List<CategoryDto> categoryDtoList = CategoryDto.toDtoList(bigCategoryList);

        return categoryDtoList;
    }

    public List<CategoryDto> getChildCategory(Long bigCategoryId) {
        List<Categories> childCategoryList = categoryRepository.findChildrenByBigCategoryId(bigCategoryId);
        List<CategoryDto> categoryDtoList = CategoryDto.toDtoList(childCategoryList);

        return categoryDtoList;
    }

    public List<Map<String, Object>> getAllCategoryGrouped() {

        List<Map<String, Object>> result = new ArrayList<>(10);

        List<Categories> bigCategoryList = categoryRepository.findBigCategory();
        List<Categories> childCategoryList = categoryRepository.findChildCategories();

        List<List<Map<String, Object>>> items = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            items.add(new ArrayList<>());  // 각 인덱스에 빈 리스트 추가
        }

        for (Categories category : childCategoryList) {
            Long parentId = category.getParentCategory().getId();
            Long id = category.getId();
            String name = category.getName();

            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", id);
            item.put("name", name);

            int index = parentId.intValue() - 1;
            items.get(index).add(item);
        }

        for (Categories categories : bigCategoryList) {
            Map<String, Object> big = new LinkedHashMap<>();
            big.put("id", categories.getId());
            big.put("name", categories.getName());
            big.put("items", items.get(categories.getId().intValue() - 1));
            result.add(big);
        }

        return result;
    }
}
