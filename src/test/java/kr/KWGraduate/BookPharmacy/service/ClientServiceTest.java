package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.ClientDto;
import kr.KWGraduate.BookPharmacy.entity.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

        ClientDto cli1 = new ClientDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        ClientDto cli2 = new ClientDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.signUp(cli1);

        assertThat(clientService.getClientsCount()).isEqualTo(1);
        clientService.signUp(cli2);

        assertThat(clientService.getClientsCount()).isEqualTo(2);

    }

    @Test
    public void joinFail(){
        ClientDto cli1 = new ClientDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        //ClientDto cli2 = new ClientDto("123","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.signUp(cli1);

        assertThat(clientService.signUp(cli1)).isEqualTo(false);
    }
    @Test
    public void basicCRUD(){
        ClientDto cli1 = new ClientDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        ClientDto cli2 = new ClientDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.signUp(cli1);
        clientService.signUp(cli2);

        cli1.setNickname("sdgsdf");
        clientService.updateClient(cli1);

        Client findCli = clientService.findById(cli1.getId());
        assertThat(findCli.getNickname()).isEqualTo("sdgsdf");

        assertThat(clientService.getClientsCount()).isEqualTo(2);

        clientService.removeClient(findCli.getId());
        assertThat(clientService.getClientsCount()).isEqualTo(1);


    }
    @Test
    public void Login(){
        ClientDto cli1 = new ClientDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        clientService.signUp(cli1);

        assertThat(clientService.canLogin("123","4321")).isEqualTo(true);

    }
}