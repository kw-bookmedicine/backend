package kr.KWGraduate.BookPharmacy.domain.keyword.service;

import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.keyword.dto.response.BoardQuestionAndDistractorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword.*;

@Service
@RequiredArgsConstructor
public class BoardKeywordService {
    private final KeywordBiMapService keywordBiMapService;

    public List<String> getBoardKeywords(){
        Keyword[] keywords = Arrays.copyOfRange(values(), 0, values().length - 1);
        return Arrays.stream(keywords)
                .map(keywordBiMapService::getKoreanKeyword)
                .collect(Collectors.toList());
    }
    public List<BoardQuestionAndDistractorDto> getQuestionAndDistractor(Keyword keyword){
        if(keyword == Common){
            //예외처리
        }
        List<BoardQuestionAndDistractorDto> commonList = convertList(Common);
        commonList.addAll(convertList(keyword));
        return commonList;
    }

    private static List<BoardQuestionAndDistractorDto> convertList(Keyword keyword) {
        return keyword.getQuesAndDis().stream()
                .map(BoardQuestionAndDistractorDto::new)
                .collect(Collectors.toList());
    }
}
