package kr.KWGraduate.BookPharmacy.domain.client.domain;

import jakarta.persistence.*;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.ClientKeyword;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientUpdateDto;
import kr.KWGraduate.BookPharmacy.global.common.BaseTimeEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","loginId", "name", "nickname", "email"})
@Table(indexes = {
        @Index(name = "client_login_id_index", columnList = "login_id", unique = true),
        @Index(name = "client_nickname_index", columnList = "nickname", unique = true),
        @Index(name = "client_email_index", columnList = "email", unique = true)
})
public class Client extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private LocalDate birth;
    private String role;
    private Integer passwordLength;
    private String description;
    private int boardCount;
    private int prescriptionCount;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientKeyword> clientKeywords = new ArrayList<>();

    public enum Gender {
        M, F;
    }

    public enum Occupation {
        STUDENT("학생"), //학생
        OFFICE_WORKER("직장인"), //직장인
        SOLE_PROPRIETORSHIP("자영업자"), //자영업자
        FREELANCER("프리랜서"), // 프리랜서
        UNEMPLOYED("무직") // 무직
        ;

        private String koreanOccupation;
        Occupation(String koreanOccupation){
            this.koreanOccupation = koreanOccupation;
        }

        public String getKoreanOccupation(){
            return this.koreanOccupation;
        }
    }

    /**
     * 유저 생성(id와 닉네임에 대한 중복확인이 끝났다고 가정)
    * */
    @Builder
    public Client(Long id, String loginId, String password, String name, LocalDate birth, String nickname,
                  String email, Gender gender, Occupation occupation, String role, Integer passwordLength, String description) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.occupation = occupation;
        this.role = role;
        this.passwordLength = passwordLength;
        this.description = description;
    }

    public void setPassword(String password, Integer passwordLength) {
        this.password = password;
        this.passwordLength = passwordLength;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
    public void setDescription(String description) {this.description = description;}

    public void plusBoardCount() {
        this.boardCount += 1;
    }

    public void minusBoardCount() {
        if(this.boardCount > 0) {
            this.boardCount -= 1;
        } else{
            this.boardCount = 0;
        }
    }

    public void plusOneLineCount() {
        this.prescriptionCount += 1;
    }

    public void minusOneLineCount() {
        if(this.prescriptionCount > 0) {
            this.prescriptionCount -= 1;
        } else{
            this.prescriptionCount = 0;
        }
    }

    public void update(Occupation occupation, String description){
        setOccupation(occupation);
        setDescription(description);
    }

}
