package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class KeywordItemDto {

    private String name;

    @Builder
    public KeywordItemDto(String name) {
        this.name = name;
    }

    public KeywordItemDto(KeywordItem keywordItem){
        this.name = keywordItem.getName();
    }

    public static KeywordItemDto toDto(KeywordItem keywordItem){
        KeywordItemDto keywordItemDto = new KeywordItemDto(keywordItem);

        return keywordItemDto;
    }

    /**
     * KeywordItem리스트를 KeywordItemDto리스트로 변환하는 함수
     * */
    public static List<KeywordItemDto> toDtoList(List<KeywordItem> keywordItemList) {
        List<KeywordItemDto> keywordItemDtoList = keywordItemList.stream()
                .map(keywordItem -> new KeywordItemDto(keywordItem))
                .collect(Collectors.toList());
        return keywordItemDtoList;
    }
}
