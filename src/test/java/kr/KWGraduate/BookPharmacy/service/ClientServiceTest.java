package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientResponseDto;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistEmailException;
import kr.KWGraduate.BookPharmacy.domain.client.service.ClientService;
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



        ClientResponseDto findCli = clientService.findById(cli1.getUsername());

        assertThat(findCli.getNickname()).isEqualTo("sdgsdf");

        assertThat(clientService.getClientsCount()).isEqualTo(2);



    }
    
}