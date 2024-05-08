package kr.KWGraduate.BookPharmacy.domain.client.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Client.Occupation occupation;

    @Builder
    public ClientJoinDto(String username, String password, String name, String nickname, String email, Client.Gender gender, Client.Occupation occupation) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.occupation = occupation;
    }
    public Client toEntity(){
        return Client.builder()
                .loginId(username)
                .password(password)
                .name(name)
                .nickname(nickname)
                .email(email)
                .gender(gender)
                .occupation(occupation)
                .role("ROLE_USER")
                .build();
    }
    public static ClientJoinDto toDto(Client client){
        return ClientJoinDto.builder()
                .username(client.getLoginId())
                .password(client.getPassword())
                .name(client.getName())
                .nickname(client.getNickname())
                .email(client.getEmail())
                .gender(client.getGender())
                .occupation(client.getOccupation())
                .build();
    }
}
