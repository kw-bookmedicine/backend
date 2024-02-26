package kr.KWGraduate.BookPharmacy.entity.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenTest {
    @Test
    void createTest() throws InterruptedException {
        RefreshToken token = RefreshToken.builder()
                .loginId("sim")
                .accessToken("sdfsdf")
                .refreshToken("sdfsdf")
                .build();
        Assertions.assertThat(token.getLoginId()).isEqualTo("sim");

    }
}