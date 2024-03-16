package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.client.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.exception.status.ExistEmailException;
import kr.KWGraduate.BookPharmacy.exception.status.IsNotSamePasswordException;
import kr.KWGraduate.BookPharmacy.exception.status.NoExistIdException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @Test
    public void join(){

        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        ClientJoinDto cli2 = new ClientJoinDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.signUp(cli1);

        assertThat(clientService.getClientsCount()).isEqualTo(1);
        clientService.signUp(cli2);

        assertThat(clientService.getClientsCount()).isEqualTo(2);

    }

    @Test
    public void joinFail(){
        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        ClientJoinDto cli2 = new ClientJoinDto("124","4321","sim","sdfs","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.signUp(cli1);

        Assertions.assertThrows(ExistEmailException.class,() ->clientService.signUp(cli2));
    }
    @Test
    public void basicCRUD(){
        //코드 수정해야힘
        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        ClientJoinDto cli2 = new ClientJoinDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, Client.Occupation.UNEMPLOYED);

        clientService.signUp(cli1);
        clientService.signUp(cli2);



        Client findCli = clientService.findById(cli1.getUsername());

        assertThat(findCli.getNickname()).isEqualTo("sdgsdf");

        assertThat(clientService.getClientsCount()).isEqualTo(2);

        clientService.removeClient(findCli.getId());
        assertThat(clientService.getClientsCount()).isEqualTo(1);


    }
    @Test
    public void Login(){
        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, Client.Occupation.UNEMPLOYED);
        clientService.signUp(cli1);

        assertThat(clientService.Login("123","4321")).isEqualTo(new ClientLoginDto("123","4321"));

        Assertions.assertThrows(NoExistIdException.class,() -> clientService.Login("121","4321"));
        Assertions.assertThrows(IsNotSamePasswordException.class,() -> clientService.Login("123","431"));

    }
}