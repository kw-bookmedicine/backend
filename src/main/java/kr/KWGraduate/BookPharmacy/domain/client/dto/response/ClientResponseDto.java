package kr.KWGraduate.BookPharmacy.domain.client.dto.response;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientResponseDto {

    private String loginId;
    private String name;
    private String nickname;
    private String email;
    private Client.Gender gender;
    private Client.Occupation occupation;
    private LocalDate birth;
    private Integer passwordLength;
    private String description;

    @Builder
    public ClientResponseDto(String loginId,String name, String nickname, String email, Client.Gender gender, Client.Occupation occupation, LocalDate birth, Integer passwordLength, String description) {
        this.loginId = loginId;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.occupation = occupation;
        this.birth = birth;
        this.passwordLength = passwordLength;
        this.description = description;
    }
    public static ClientResponseDto toDto(Client client){
        return ClientResponseDto.builder()
                .loginId(client.getLoginId())
                .name(client.getName())
                .nickname(client.getNickname())
                .email(client.getEmail())
                .gender(client.getGender())
                .occupation(client.getOccupation())
                .birth(client.getBirth())
                .passwordLength(client.getPasswordLength())
                .description(client.getDescription())
                .build();
    }
}
