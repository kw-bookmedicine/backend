package kr.KWGraduate.BookPharmacy.domain.category.dto.response;

import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CategoryDto {

    private Long id;
    private String name;
    private int level;

    public CategoryDto(Categories category){
        this.id = category.getId();
        this.name = category.getName();
        this.level = category.getLevel();
    }

    @Builder
    public CategoryDto(Long id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public static List<CategoryDto> toDtoList(List<Categories> categoryList){
        List<CategoryDto> dtoList = categoryList.stream().map(category -> new CategoryDto(category))
                .collect(Collectors.toList());

        return dtoList;
    }
}
