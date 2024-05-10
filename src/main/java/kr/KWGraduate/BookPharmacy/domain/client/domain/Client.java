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
        STUDENT, //학생
        OFFICE_WORKER, //직장인
        SOLE_PROPRIETORSHIP, //자영업자
        FREELANCER, // 프리랜서
        UNEMPLOYED // 무직
        ;
    }

    /**
     * 유저 생성(id와 닉네임에 대한 중복확인이 끝났다고 가정)
    * */
    @Builder
    public Client(Long id, String loginId, String password, String name, LocalDate birth, String nickname, String email, Gender gender, Occupation occupation, String role, Integer passwordLength, String description) {

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

    public void setPassword(String password) {
        this.password = password;
        this.passwordLength = password.length();
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
    public void setDescription(String description) {this.description = description;}

    //비즈니스 로직 추가
    public boolean isEqualPassword(String password){
        if (this.password.equals(password)) {
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isEqualName(String name){
        if (this.name.equals(name)) {
            return true;
        }
        else{
            return false;
        }
    }

    public void update(ClientUpdateDto clientUpdateDto){
        this.setPassword(clientUpdateDto.getPassword());
        this.setNickname(clientUpdateDto.getNickname());
        this.setOccupation(clientUpdateDto.getOccupation());
    }

}
