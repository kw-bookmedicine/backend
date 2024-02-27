package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.BookKeyword;
import lombok.Data;

@Data
public class BookKeywordDto {

    private String name;

    public BookKeywordDto(BookKeyword bookKeyword){
        this.name = bookKeyword.getKeywordItem().getName();
    }
}
