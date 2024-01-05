package kr.KWGraduate.BookPharmacy.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ClientTest {
    @Test
    public void equaltest(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        Assertions.assertThat(cli1.isEqualName("ha")).isEqualTo(true);
        Assertions.assertThat(cli1.isEqualPassword("321")).isEqualTo(false);

    }


}