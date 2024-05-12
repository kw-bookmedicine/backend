package kr.KWGraduate.BookPharmacy.domain.client.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@NotNull
public class ClientJoinDto {

    private String username;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private Client.Gender gender;
    private String occupation;
    private LocalDate birth;

    @Builder
    public ClientJoinDto(String username, String password, String name, String nickname, String email, Client.Gender gender, String occupation,LocalDate birth) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.occupation = occupation;
        this.birth = birth;
    }
    public Client toEntity(int passwordLength, Client.Occupation occupation){
        return Client.builder()
                .loginId(username)
                .password(password)
                .name(name)
                .nickname(nickname)
                .email(email)
                .gender(gender)
                .occupation(occupation)
                .birth(birth)
                .role("ROLE_USER")
                .passwordLength(passwordLength)
                .description("안녕하세요! 책을 좋아하는 "+nickname+"입니다")
                .build();
    }

}
