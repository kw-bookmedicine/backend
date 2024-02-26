package kr.KWGraduate.BookPharmacy.repository.redis;

import kr.KWGraduate.BookPharmacy.entity.redis.RefreshToken;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RefreshTokenRepositoryTest {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void before(){
        refreshTokenRepository.deleteAll();
    }

    @Test
    void saveTest(){
        RefreshToken token = RefreshToken.builder()
                .loginId("sim12")
                .refreshToken("sdfsdf")
                .accessToken("sdfsdf")
                .build();

        refreshTokenRepository.save(token);

        Optional<RefreshToken> findToken = refreshTokenRepository.findById("sim12");
        System.out.println(findToken.get().getLoginId());
        Assertions.assertThat(token).isEqualTo(findToken.get());
    }
    @Test
    void simpleTest(){
        RefreshToken token = RefreshToken.builder()
                .loginId("sim")
                .refreshToken("sdfsdf")
                .accessToken("sdfsdf")
                .build();

        refreshTokenRepository.save(token);

        Optional<RefreshToken> findToken = refreshTokenRepository.findById("sim");

        System.out.println(refreshTokenRepository.count());
        for(var s : refreshTokenRepository.findAll()){
            System.out.println(s.getLoginId());
        }
        Assertions.assertThat(token).isEqualTo(findToken.get());
    }
    @Test
    void expiredTest(){
        RefreshToken token = RefreshToken.builder()
                .loginId("sim")
                .refreshToken("sdfsdf")
                .accessToken("sdfsdf")
                .build();

        RefreshToken token2 = RefreshToken.builder()
                .loginId("sim")
                .refreshToken("sdfsdf2222")
                .accessToken("sdfsdf22222")
                .build();

        refreshTokenRepository.save(token);

        refreshTokenRepository.save(token2);


        Optional<RefreshToken> findToken = refreshTokenRepository.findById("sim");

        RefreshToken refreshToken = findToken.get();
        System.out.println(refreshToken.getLoginId());
        Assertions.assertThat(token2).isEqualTo(refreshToken);


    }

    @Test
    void manyToken(){
        RefreshToken token = RefreshToken.builder()
                .loginId("sim111")
                .refreshToken("sdfsdf")
                .accessToken("sdfsdf")
                .build();

        RefreshToken token2 = RefreshToken.builder()
                .loginId("sim2222")
                .refreshToken("sdfsdf2222")
                .accessToken("sdfsdf22222")
                .build();
        RefreshToken token3 = RefreshToken.builder()
                .loginId("sim3333")
                .refreshToken("sdfsdf")
                .accessToken("sdfsdf")
                .build();

        RefreshToken token4 = RefreshToken.builder()
                .loginId("sim4444")
                .refreshToken("sdfsdf2222")
                .accessToken("sdfsdf22222")
                .build();
        RefreshToken token5 = RefreshToken.builder()
                .loginId("sim5555")
                .refreshToken("sdfsdf")
                .accessToken("sdfsdf")
                .build();

        RefreshToken token6 = RefreshToken.builder()
                .loginId("sim111")
                .refreshToken("sdfsdf6666")
                .accessToken("sdfsdf66666")
                .build();

        refreshTokenRepository.save(token);
        refreshTokenRepository.save(token2);
        refreshTokenRepository.save(token3);
        refreshTokenRepository.save(token4);
        refreshTokenRepository.save(token5);
        refreshTokenRepository.save(token6);

        Assertions.assertThat(refreshTokenRepository.count()).isEqualTo(5);

        refreshTokenRepository.deleteById("sim111");

        Assertions.assertThat(refreshTokenRepository.count()).isEqualTo(4);

    }
}