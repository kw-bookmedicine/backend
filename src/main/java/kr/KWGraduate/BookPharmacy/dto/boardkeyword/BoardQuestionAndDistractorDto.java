package kr.KWGraduate.BookPharmacy.dto.boardkeyword;

import kr.KWGraduate.BookPharmacy.entity.QuesAndDis;
import lombok.Data;

import java.util.List;

@Data
public class BoardQuestionAndDistractorDto {
    private String question;
    private List<String> distractor;

    public BoardQuestionAndDistractorDto(QuesAndDis quesAndDis){
        this.question = quesAndDis.getQuestion();
        this.distractor = quesAndDis.getDistractor();
    }
}
