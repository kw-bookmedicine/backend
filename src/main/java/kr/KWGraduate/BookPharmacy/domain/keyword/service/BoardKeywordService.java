package kr.KWGraduate.BookPharmacy.domain.keyword.service;

import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.keyword.dto.response.BoardQuestionAndDistractorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardKeywordService {
    private final KeywordBiMapService keywordBiMapService;

    public List<String> getBoardKeywords(){
        return Arrays.stream(Keyword.values())
                .map(keywordBiMapService::getKoreanKeyword)
                .collect(Collectors.toList());
    }
    public List<BoardQuestionAndDistractorDto> getQuestionAndDistractor(String koreanKeyword){
        return keywordBiMapService.getKeyword(koreanKeyword).getQuesAndDis().stream()
                .map(BoardQuestionAndDistractorDto::new)
                .collect(Collectors.toList());
    }
}
