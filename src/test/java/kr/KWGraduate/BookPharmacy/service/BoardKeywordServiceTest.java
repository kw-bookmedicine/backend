package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.boardkeyword.BoardQuestionAndDistractorDto;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BoardKeywordServiceTest {
    @Autowired
    BoardKeywordService boardKeywordService;

    @Test
    void 모든_키워드_추출_메서드(){
//        List<BoardKeywordDto> expectedKeywords = Arrays.asList(
//                new BoardKeywordDto(Keyword.Economy_Management, "경제/경영"),
//                new BoardKeywordDto(Keyword.Health, "건강"),
//                new BoardKeywordDto(Keyword.Children_Parenting, "자녀/양육"),
//                new BoardKeywordDto(Keyword.Employment_Career, "취업"),
//                new BoardKeywordDto(Keyword.Workbook_Examination, "문제집/수험서"),
//                new BoardKeywordDto(Keyword.Relationships_Communication, "관계/소통"),
//                new BoardKeywordDto(Keyword.Fiction_Essays, "소셜/에세이"),
//                new BoardKeywordDto(Keyword.Philosophy, "철학"),
//                new BoardKeywordDto(Keyword.History, "역사"),
//                new BoardKeywordDto(Keyword.Science_Math_Engineering, "수학/과학/공학"),
//                new BoardKeywordDto(Keyword.Society, "사회"),
//                new BoardKeywordDto(Keyword.Hobbies, "취미")
//        );

        List<String> expectedKeywords = List.of("경제/경영","건강","자녀/양육","취업","문제집/수험서","관계/소통","소셜/에세이","철학","역사","수학/과학/공학","사회","취미");
        List<String> boardKeywords = boardKeywordService.getBoardKeywords();

        org.junit.jupiter.api.Assertions.assertIterableEquals(boardKeywords,expectedKeywords);

    }

    @Test
    void 특정_키워드_질문_객관식_추출(){
        List<BoardQuestionAndDistractorDto> expectedList = Keyword.Economy_Management.getQuesAndDis().stream()
                .map(BoardQuestionAndDistractorDto::new)
                .collect(Collectors.toList());

        List<BoardQuestionAndDistractorDto> questionAndDistractor = boardKeywordService.getQuestionAndDistractor("경제/경영");

        Assertions.assertIterableEquals(expectedList,questionAndDistractor);
        System.out.println(expectedList);
    }
}