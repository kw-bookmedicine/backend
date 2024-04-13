package kr.KWGraduate.BookPharmacy.dto.question;

import lombok.Getter;

import java.util.List;

@Getter
public class QuesAndDis{
    private String question;
    private List<String> distractor;

    public static QuesAndDis of(String question, List<String> distractor){
        QuesAndDis quesAndDis = new QuesAndDis();
        quesAndDis.question = question;
        quesAndDis.distractor = distractor;

        return quesAndDis;
    }

}
