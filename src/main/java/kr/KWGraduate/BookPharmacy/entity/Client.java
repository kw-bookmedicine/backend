package kr.KWGraduate.BookPharmacy.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {

    @Id
    @Column(name = "client_id")
    public String id;

    public String password;
    public String name;
    public String nickname;
    public String email;

    @Enumerated(EnumType.STRING)
    public Gender gender;

    @Enumerated(EnumType.STRING)
    public Occupation occupation;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<ClientBook> clientBooks = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<ClientKeyword> clientKeywords = new ArrayList<>();

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
