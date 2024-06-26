package kr.KWGraduate.BookPharmacy.domain.client.dto.response;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientMypageDto {

    private String loginId;
    private String name;
    private String nickname;
    private String email;
    private Client.Gender gender;
    private String occupation;
    private LocalDate birth;
    private Integer passwordLength;
    private String description;
    private int boardCount;
    private int prescriptionCount;
    private Boolean isOauth;

    @Builder
    public ClientMypageDto(Client client) {
        if(client.getLoginId().startsWith("naver")){
            this.loginId = client.getLoginId().substring(6,12);
            this.isOauth = true;
        }else{
            this.loginId = client.getLoginId();
            this.isOauth = false;
        }
        this.name = client.getName();
        this.nickname = client.getNickname();
        this.email = client.getEmail();
        this.gender = client.getGender();
        this.occupation = client.getOccupation().getKoreanOccupation();
        this.birth = client.getBirth();
        this.passwordLength = client.getPasswordLength();
        this.description = client.getDescription();
        this.boardCount = client.getBoardCount();
        this.prescriptionCount = client.getPrescriptionCount();
    }

}
