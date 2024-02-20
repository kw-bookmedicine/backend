package kr.KWGraduate.BookPharmacy.entity.redis;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken",timeToLive = 300)
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@AllArgsConstructor
public class RefreshToken {

    @Id
    private String loginId;
    private String refreshToken;

    private String accessToken;

}
