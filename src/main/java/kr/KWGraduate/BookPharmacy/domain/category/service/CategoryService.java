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

    public Map<String, List<String>> getAllCategoryGrouped() {
        List<Categories> allCategories = categoryRepository.findChildCategories();

        Map<String, List<String>> map = new LinkedHashMap<>();

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
