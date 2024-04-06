package kr.KWGraduate.BookPharmacy.enums;

import lombok.Getter;

@Getter
public enum Keyword {
    Economy("돈이 없으세요?"),
    Health("건강이 없으세요?"),
    Children_Parenting("자녀이 없으세요?"),
    Employment_Career("일자리가 없으세요?"),
    Study_SelfImprovement("공부이 없으세요?"),
    Relationships_Communication("관계이 없으세요?"),
    Fiction_Essays("소설이 없으세요?"),
    Philosophy("철학이 없으세요?"),
    History("역사가 없으세요?"),
    Science("과학이 없으세요?"),
    Society("사회가 없으세요?"),
    Hobbies("취미가 없으세요?");

    private String question;

    Keyword(String question){
        this.question = question;
    }
}
