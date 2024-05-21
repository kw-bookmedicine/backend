package kr.KWGraduate.BookPharmacy.domain.keyword.domain;

import java.util.List;

public class QuestionDistractor {
    public static final List<String> economyManagementDistractor1 =
            List.of(
                    "경제상식",
                    "경제/경영이론",
                    "경제현상",
                    "자산/투자",
                    "경영/마케팅",
                    "세무/회계",
                    "유통/창업",
                    "기타"
            );
    public static final List<String> healthDistractor1 =
            List.of(
                    "10대",
                    "20대",
                    "30대",
                    "40대",
                    "50대 이상"
            );

    public static final List<String> healthDistractor2 =
            List.of(
                    "눈/관력/근력",
                    "면역력/피로",
                    "정신/스트레스",
                    "질병",
                    "식습관/다이어트",
                    "기타"
                    );
    public static final List<String> childParentDistractor1 =
            List.of(
                    "태아",
                    "신생아(생후 28일 이내)",
                    "영/유아(만 6세 미만)",
                    "어린이(만 12세 미만)",
                    "청소년(만 19세 미만)"
            );
    public static final List<String> childParentDistractor2 =
            List.of(
                    "정서/사회성",
                    "건강/신체발달",
                    "교육",
                    "자녀와의 관계",
                    "기타"
            );
    public static final List<String> careerDistractor1 =
            List.of(
                    "진로고민",
                    "자기소개서",
                    "면접준비",
                    "기타"
            );
    public static final List<String> careerDistractor2 =
            List.of(
                    "대학생",
                    "취준생",
                    "회사원",
                    "기타"
            );
    public static final List<String> workbookDistractor1 =
            List.of(
                    "자격증",
                    "입시",
                    "외국어",
                    "자기계발 취업 & 진로"
            );
    public static final List<String> relationDistractor1 =
            List.of(
                    "부모님",
                    "친구",
                    "연인",
                    "동료"
            );
    public static final List<String> relationDistractor2 =
            List.of(
                    "다툼",
                    "상처",
                    "소외감",
                    "무시",
                    "의사소통",
                    "기타"
            );
    public static final List<String> fictionDistractor1 =
            List.of(
                    "단순 재미",
                    "문제 해결",
                    "동기 부여",
                    "감성 치유"
            );
    public static final List<String> fictionDistractor2 =
            List.of(
                    "고전/문학",
                    "공포/호러",
                    "추리/미스터리",
                    "역사",
                    "과학/SF",
                    "판타지",
                    "로맨스",
                    "무협",
                    "청소년/어린이",
                    "에세이",
                    "기타"
            );
    public static final List<String> philosophyDistractor1 =
            List.of(
                    "플라톤",
                    "아리스토텔레스",
                    "데카르트",
                    "니체",
                    "칸트",
                    "소크라세테스",
                    "잘 모른다"
            );
    public static final List<String> historyDistractor1 =
            List.of(
                    "한국사",
                    "유럽",
                    "아메리카",
                    "아프리카",
                    "동아시아",
                    "고대 문명",
                    "중세와 르네상스",
                    "근대사",
                    "현대사",
                    "기타"
            );
    public static final List<String> historyDistractor2 =
            List.of(
                    "문화와 예술",
                    "정치와 전쟁",
                    "과학과 기술",
                    "사상과 철학",
                    "일상과 생활방식"
            );

    public static final List<String> scienceDistractor1 =
            List.of(
                    "물리",
                    "화학",
                    "생물",
                    "지구과학",
                    "천문학/우주",
                    "컴퓨터",
                    "전자",
                    "건축",
                    "기계",
                    "기타"
            );

    public static final List<String> scienceDistractor2 =
            List.of(
                    "교양과 일상",
                    "기초 이론",
                    "최신 발견",
                    "실용적 적용",
                    "사회적 영향"
            );

    public static final List<String> societyDistractor1 =
            List.of(
                    "기후 변화 및 환경 문제"
                    ,"불평등 및 인종차별"
                    ,"저출산 및 초고령화 사회"
                    ,"고용불안"
                    ,"공동체 갈등"
                    ,"국제 정세 및 안보"
                    ,"경제 및 산업"
                    ,"기타"
            );

    public static final List<String> societyDistractor2 =
            List.of("작성해주세요");

    public static final List<String> hobbyDistractor1 =
            List.of(
                    "음악",
                    "미술",
                    "요리"
                    ,"운동"
                    ,"독서"
                    ,"여행"
                    ,"기타"
            );

    public static final List<String> commonDistractor1 =
            List.of(
                    "완전 모른다",
                    "모른다",
                    "보통이다",
                    "알고 있다",
                    "자세히 알고 있다"
            );

    public static final List<String> commonDistractor2 =
            List.of(
                    "2권 미만",
                    "2권 이상 5권 미만",
                    "5권 이상"
            );
}
