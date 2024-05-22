package kr.KWGraduate.BookPharmacy.domain.keyword.domain;

import lombok.Getter;

import java.util.List;

import static kr.KWGraduate.BookPharmacy.domain.keyword.domain.BoardKeywordQuestion.*;
import static kr.KWGraduate.BookPharmacy.domain.keyword.domain.QuestionDistractor.*;

@Getter
public enum Keyword {

    Economy_Management(List.of(
            QuesAndDis.of(economyManagementQues1, economyManagementDistractor1)
    )),
    Health(List.of(
            QuesAndDis.of(healthQues1, healthDistractor1),
            QuesAndDis.of(healthQues2, healthDistractor2)
    )),
    Children_Parenting(List.of(
            QuesAndDis.of(childParentQues1, childParentDistractor1),
            QuesAndDis.of(childParentQues2, childParentDistractor2)
    )),
    Employment_Career(List.of(
            QuesAndDis.of(careerQues1, careerDistractor1),
            QuesAndDis.of(careerQues2, careerDistractor2)
    )),
    Workbook_Examination(List.of(
            QuesAndDis.of(workbookQues1, workbookDistractor1)
    )),
    Relationships_Communication(List.of(
            QuesAndDis.of(relationQues1, relationDistractor1),
            QuesAndDis.of(relationQues2, relationDistractor2)
    )),
    Fiction_Essays(List.of(
            QuesAndDis.of(fictionQues1, fictionDistractor1),
            QuesAndDis.of(fictionQues2, fictionDistractor2)
    )),
    Philosophy(List.of(
            QuesAndDis.of(philosophyQues1, philosophyDistractor1)
    )),
    History(List.of(
            QuesAndDis.of(historyQues1, historyDistractor1),
            QuesAndDis.of(historyQues2, historyDistractor2)
    )),
    Science_Math_Engineering(List.of(
            QuesAndDis.of(scienceQues1, scienceDistractor1),
            QuesAndDis.of(scienceQues2, scienceDistractor2)
    )),
    Society(List.of(
            QuesAndDis.of(societyQues1, societyDistractor1),
            QuesAndDis.of(societyQues2, societyDistractor2)
    )),
    Hobbies(List.of(QuesAndDis.of(hobbyQues1, hobbyDistractor1))),
    ETC(List.of()),
    Common(List.of(
            QuesAndDis.of(commonQue1,commonDistractor1),
            QuesAndDis.of(commonQue2,commonDistractor2)
    ));

    private final List<QuesAndDis> quesAndDis;


    Keyword(List<QuesAndDis> quesAndDis){
        this.quesAndDis = quesAndDis;
    }
}


