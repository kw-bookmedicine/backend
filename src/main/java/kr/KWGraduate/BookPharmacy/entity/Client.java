package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name", "nickname", "email"})
public class Client {

    @Id
    @Column(name = "client_id")
    private String id;

    private String password;
    private String name;
    private String nickname;
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Occupation occupation;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<ClientBook> clientBooks = new ArrayList<>();

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
    public Client(String id, String password, String name, String nickname, String email, Gender gender, Occupation occupation) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.occupation = occupation;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
