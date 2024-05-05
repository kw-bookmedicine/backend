package kr.KWGraduate.BookPharmacy.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import kr.KWGraduate.BookPharmacy.dto.boardkeyword.BoardKeywordDto;
import kr.KWGraduate.BookPharmacy.dto.boardkeyword.BoardQuestionAndDistractorDto;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardKeywordService {
    private BiMap<Keyword,String> keywordMap = HashBiMap.create();

    public BoardKeywordService(){
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
    public List<BoardKeywordDto> getBoardKeywords(){
        return Arrays.stream(Keyword.values())
                .map(keyword -> new BoardKeywordDto(keyword, keywordMap.get(keyword)))
                .collect(Collectors.toList());
    }
    public List<BoardQuestionAndDistractorDto> getQuestionAndDistractor(Keyword keyword){
        return keyword.getQuesAndDis().stream()
                .map(BoardQuestionAndDistractorDto::new)
                .collect(Collectors.toList());
    }
}
