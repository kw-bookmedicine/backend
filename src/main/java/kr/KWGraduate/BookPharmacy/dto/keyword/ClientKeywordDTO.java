package kr.KWGraduate.BookPharmacy.dto.keyword;

import kr.KWGraduate.BookPharmacy.entity.ClientKeyword;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientKeywordDTO {
    Long id;
    List<String> keywords = new ArrayList<>();

    @Builder
    public ClientKeywordDTO(Long id, List<KeywordItem> keywordItems){
        this.id = id;
        for(KeywordItem k : keywordItems){
            keywords.add(k.getName());
        }
    }
}
