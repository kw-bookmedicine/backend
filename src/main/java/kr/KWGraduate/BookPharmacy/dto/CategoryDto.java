package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Categories;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDto {

    private String name;
    private int level;

    public CategoryDto(Categories category){
        this.name = category.getName();
        this.level = category.getLevel();
    }

    @Builder
    public CategoryDto(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public static List<CategoryDto> toDtoList(List<Categories> categoryList){
        List<CategoryDto> dtoList = categoryList.stream().map(category -> new CategoryDto(category))
                .collect(Collectors.toList());

        return dtoList;
    }
}
