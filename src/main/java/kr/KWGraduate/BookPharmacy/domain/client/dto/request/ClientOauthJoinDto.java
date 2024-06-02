package kr.KWGraduate.BookPharmacy.domain.client.dto.request;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2.Oauth2SignUpInfo;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ClientOauthJoinDto {
    private String nickname;
    private Client.Gender gender;
    private String occupation;
    private LocalDate birth;
    private List<String> interestList;
    @Builder
    public ClientOauthJoinDto(String nickname, Client.Gender gender, String occupation,LocalDate birth, List<String> interestList) {
        this.nickname = nickname;
        this.gender = gender;
        this.occupation = occupation;
        this.birth = birth;
        this.interestList = interestList;
    }
    public Client toEntity(Oauth2SignUpInfo oauth2SignUpInfo, Client.Occupation occupation, String password){
        return Client.builder()
                .loginId(oauth2SignUpInfo.getProviderId())
                .password(password)
                .name(oauth2SignUpInfo.getName())
                .nickname(nickname)
                .email(oauth2SignUpInfo.getEmail())
                .gender(gender)
                .occupation(occupation)
                .birth(birth)
                .role("ROLE_USER")
                .passwordLength(9)
                .description("안녕하세요! 책을 좋아하는 "+nickname+"입니다")
                .build();
    }
}
