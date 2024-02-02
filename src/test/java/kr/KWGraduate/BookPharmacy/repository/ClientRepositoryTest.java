package kr.KWGraduate.BookPharmacy.repository;

import jakarta.persistence.EntityManager;
import kr.KWGraduate.BookPharmacy.entity.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EntityManager em;

    @Test
    public void integratedTest(){
        Client cli1 = new Client(1L,"123","4321","ha", LocalDate.now(),"spqjf", "lsh@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client saveClient1 = clientRepository.save(cli1);

        Client cli2 = new Client(2L,"124","4321","sim",LocalDate.now(),"sdfsdgs", "lsh2@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client saveClient2 = clientRepository.save(cli2);


        Assertions.assertThat(clientRepository.count()).isEqualTo(2);

        List<Client> allClients = clientRepository.findAll();


        Assertions.assertThat(allClients).containsExactly(saveClient1,saveClient2);

        Long savedId = saveClient1.getId();

        String findCli1Name = clientRepository.findById(savedId).get().getName();
        Assertions.assertThat(findCli1Name).isEqualTo(cli1.getName());

        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class,() -> clientRepository.findById(33L).get().getName());


        clientRepository.delete(cli1);

        Assertions.assertThat(clientRepository.count()).isEqualTo(1);

    }


    @Test
    public void findByEmailAndNickname(){
        Client cli1 = new Client(1L,"123","4321","ha", LocalDate.now(),"spqjf", "lsh@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        Client cli2 = new Client(2L,"124","4321","sim",LocalDate.now(),"sdfsdgs", "lsh2@naver", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientRepository.save(cli1);
        clientRepository.save(cli2);

        Assertions.assertThat(clientRepository.count()).isEqualTo(2);


        Optional<Client> nickClient = clientRepository.findByNickname(cli1.getNickname());

        Assertions.assertThat(nickClient.get().getLoginId()).isEqualTo(cli1.getLoginId());

        Optional<Client> emailClient = clientRepository.findByEmail("dfgdf");
        Assertions.assertThat(emailClient.isEmpty()).isEqualTo(true);

    }


}