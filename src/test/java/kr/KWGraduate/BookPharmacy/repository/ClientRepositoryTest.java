package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Transactional
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Test
    public void integratedTest(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client saveClient1 = clientRepository.save(cli1);

        Client cli2 = new Client("124","4321","sim","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client saveClient2 = clientRepository.save(cli2);

        Assertions.assertThat(clientRepository.count()).isEqualTo(2);

        List<Client> allClients = clientRepository.findAll();


        Assertions.assertThat(allClients).containsExactly(saveClient1,saveClient2);

        String savedId = saveClient1.getId();

        String findCli1Name = clientRepository.findById(savedId).get().getName();
        Assertions.assertThat(findCli1Name).isEqualTo(cli1.getName());

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,() -> clientRepository.findById("sdkfjhsdfkghjfd").get().getName());


        clientRepository.delete(cli1);

        Assertions.assertThat(clientRepository.count()).isEqualTo(1);

    }


    @Test
    public void findByEmailAndNickname(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        Client cli2 = new Client("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientRepository.save(cli1);
        clientRepository.save(cli2);

        Assertions.assertThat(clientRepository.count()).isEqualTo(2);


        Optional<Client> nickClient = clientRepository.findByNickname(cli1.getNickname());

        Assertions.assertThat(nickClient.get().getId()).isEqualTo(cli1.getId());

        Optional<Client> emailClient = clientRepository.findByEmail("dfgdf");
        Assertions.assertThat(emailClient.isEmpty()).isEqualTo(true);

    }


}