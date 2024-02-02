package kr.KWGraduate.BookPharmacy.dto;

import kr.KWGraduate.BookPharmacy.entity.Client;
import lombok.Builder;
import lombok.Data;

@Data
public class ClientDto {

    private Long id;
    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private String email;
    private Client.Gender gender;
    private Client.Occupation occupation;

    @Builder
    public ClientDto(Long id, String loginId, String password, String name, String nickname, String email, Client.Gender gender, Client.Occupation occupation) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.occupation = occupation;
    }
    public Client toEntity(){
        return Client.builder()
                .id(id)
                .loginId(loginId)
                .password(password)
                .name(name)
                .nickname(nickname)
                .email(email)
                .gender(gender)
                .occupation(occupation)
                .build();
    }
    public ClientDto toDto(Client client){
        return ClientDto.builder()
                .id(client.getId())
                .loginId(client.getLoginId())
                .password(client.getPassword())
                .name(client.getName())
                .nickname(client.getNickname())
                .email(client.getEmail())
                .gender(client.getGender())
                .occupation(client.getOccupation())
                .build();
    }
}
