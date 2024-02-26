package kr.KWGraduate.BookPharmacy.service.redis;

import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.entity.redis.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenServiceTest {

    @Autowired
    RefreshTokenService tokenService;

    @Test
    void saveTest(){
        TokenDto token = TokenDto.builder()
                .grantType("sdfsd")
                .accessToken("sdfsd")
                .refreshToken("sdfsd")
                .build();
        tokenService.save(token,"sim");

        RefreshToken sim = tokenService.findByUsername("sim");

        RefreshToken refreshToken = RefreshToken.builder()
                .loginId("sim")
                .refreshToken("sdfsd")
                .accessToken("sdfsd")
                .build();
        Assertions.assertThat(refreshToken).isEqualTo(sim);
    }
}