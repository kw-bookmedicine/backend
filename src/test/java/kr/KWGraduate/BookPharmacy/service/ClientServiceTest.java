package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.entity.Client;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @Test
    public void join(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        Client cli2 = new Client("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.joinClient(cli1);

        assertThat(clientService.getClientsCount()).isEqualTo(1);
        clientService.joinClient(cli2);

        assertThat(clientService.getClientsCount()).isEqualTo(2);

    }

    @Test
    public void joinFail(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        Client cli2 = new Client("123","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.joinClient(cli1);


        assertThrows(IllegalArgumentException.class, () -> clientService.joinClient(cli2));


    }
    @Test
    public void basicCRUD(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        Client cli2 = new Client("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.joinClient(cli1);
        clientService.joinClient(cli2);
        clientService.updateClient(cli1.getId(), "1232", "sdgsdf", Client.Occupation.FREELANCER);

        Client findCli = clientService.findById(cli1.getId());
        assertThat(findCli.getNickname()).isEqualTo("sdgsdf");

        assertThat(clientService.getClientsCount()).isEqualTo(2);

        clientService.removeClient(findCli);
        assertThat(clientService.getClientsCount()).isEqualTo(1);


    }
    @Test
    public void canLogin(){
        Client cli1 = new Client("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        clientService.joinClient(cli1);

        assertThat(clientService.canLogin("123","4321")).isEqualTo(true);

    }
}