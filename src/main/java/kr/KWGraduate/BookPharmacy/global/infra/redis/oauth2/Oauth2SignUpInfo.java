package kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.UUID;

@Getter
@RedisHash(value = "oauth2SignUpInfo",timeToLive = 3600)
@NoArgsConstructor
@EqualsAndHashCode
public class Oauth2SignUpInfo {
    @Id
    private String email;
    private String name;
    private String providerId;
    private String password;

    @Builder
    public Oauth2SignUpInfo(String email, String name, String providerId){
        this.email = email;
        this.name = name;
        this.providerId = providerId;
        this.password = Integer.toString((email + name + providerId).hashCode());
    }
}
