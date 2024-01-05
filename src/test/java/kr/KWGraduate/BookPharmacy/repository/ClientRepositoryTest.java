package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

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
    public void test(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        String saveId1 = clientRepository.save(cli1);

        Client cli2 = new Client("124","4321","sim","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        String saveId2 = clientRepository.save(cli1);

        System.out.println(clientRepository.count());

        for (Client client : clientRepository.findAll()) {
            System.out.println(client.getName());
        }
        System.out.println(clientRepository.findById(saveId1).getName());

        clientRepository.delete(cli1);

        System.out.println(clientRepository.count());

    }
}