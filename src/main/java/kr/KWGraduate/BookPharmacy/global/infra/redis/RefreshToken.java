package kr.KWGraduate.BookPharmacy.global.infra.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken",timeToLive = 3600)
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String loginId;
    private String refreshToken;

    private String accessToken;
    boolean isLogout;

    public void setLogout(){
        isLogout = true;
    }
}
