package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void testSaveClient(){
        Client cli1 = new Client("123","4321","sim","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        String saveId = clientRepository.save(cli1);
        Assertions.assertThat(cli1.getId()).isEqualTo(saveId);
    }
    @Test
    public void integratedTest(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        String saveId1 = clientRepository.save(cli1);

        Client cli2 = new Client("124","4321","sim","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        String saveId2 = clientRepository.save(cli2);

        Assertions.assertThat(clientRepository.count()).isEqualTo(2);

        List<Client> allClients = clientRepository.findAll();
        Assertions.assertThat(allClients).containsExactly(cli1,cli2);

        String findCli1Name = clientRepository.findById(saveId1).getName();
        Assertions.assertThat(findCli1Name).isEqualTo(cli1.name);


        clientRepository.delete(cli1);

        Assertions.assertThat(clientRepository.count()).isEqualTo(1);

    }


}