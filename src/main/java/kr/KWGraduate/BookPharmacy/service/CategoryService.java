package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.CategoryDto;
import kr.KWGraduate.BookPharmacy.entity.Categories;
import kr.KWGraduate.BookPharmacy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<CategoryDto> getChildCategory(String categoryName) {
        List<Categories> childCategoryList = categoryRepository.findChildrenByBigCategoryName(categoryName);
        List<CategoryDto> categoryDtoList = CategoryDto.toDtoList(childCategoryList);

        return categoryDtoList;
    }

    public Map<String, List<String>> getAllCategoryGrouped() {
        List<Categories> allCategories = categoryRepository.findChildCategories();

        Map<String, List<String>> map = new HashMap<>();

        for (Categories category : allCategories) {
            String key = category.getParentCategory().getName();
            String value = category.getName();
            // 키가 이미 존재하는지 확인
            if (map.containsKey(key)) {
                // 키가 이미 존재하면 기존 리스트에 추가
                map.get(key).add(value);
            } else {
                // 키가 존재하지 않으면 새로운 리스트를 생성하고 추가
                List<String> newList = new ArrayList<>();
                newList.add(value);
                map.put(key, newList);
            }
        }

        return map;
    }
}
