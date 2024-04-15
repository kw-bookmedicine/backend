package kr.KWGraduate.BookPharmacy.entity;

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
