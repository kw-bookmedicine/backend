package kr.KWGraduate.BookPharmacy.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import org.springframework.stereotype.Service;

@Service
public class KeywordBiMapService {
    private BiMap<Keyword,String> keywordMap = HashBiMap.create();

    public KeywordBiMapService(){
        keywordMap.put(Keyword.Health,"건강");
        keywordMap.put(Keyword.Economy_Management,"경제/경영");
        keywordMap.put(Keyword.Relationships_Communication, "관계/소통");
        keywordMap.put(Keyword.Fiction_Essays, "소셜/에세이");
        keywordMap.put(Keyword.Children_Parenting, "자녀/양육");
        keywordMap.put(Keyword.Philosophy, "철학");
        keywordMap.put(Keyword.History, "역사");
        keywordMap.put(Keyword.Science_Math_Engineering, "수학/과학/공학");
        keywordMap.put(Keyword.Workbook_Examination, "문제집/수험서");
        keywordMap.put(Keyword.Employment_Career, "취업");
        keywordMap.put(Keyword.Society, "사회");
        keywordMap.put(Keyword.Hobbies, "취미");
    }
    public Keyword getKeyword(String koreanKeyword){
        return keywordMap.inverse().get(koreanKeyword);
    }
    public String getKoreanKeyword(Keyword keyword){
        return keywordMap.get(keyword);
    }
}
