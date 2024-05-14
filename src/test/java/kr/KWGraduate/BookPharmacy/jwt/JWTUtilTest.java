package kr.KWGraduate.BookPharmacy.jwt;

import kr.KWGraduate.BookPharmacy.global.security.common.dto.TokenDto;
import kr.KWGraduate.BookPharmacy.global.security.common.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.security.SignatureException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JWTUtilTest {
    @Autowired
    JWTUtil jwtUtil;

    @Test
    public void createToken() throws SignatureException {
        TokenDto jwt = jwtUtil.createJwt("sim", "USER","false");
        System.out.println(jwt);

        Authentication authentication = jwtUtil.getAuthentication(jwt.getAccessToken());
        System.out.println(authentication);

    }

}