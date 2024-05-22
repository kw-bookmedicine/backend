package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistEmailException;
import kr.KWGraduate.BookPharmacy.domain.client.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ClientServiceTest {

    @Autowired
    ClientService clientService;

    @Test
    public void join(){

        List<String> interestList = List.of("영어", "독일어", "북아프리카");
        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, "무직", LocalDate.now(), interestList);
        ClientJoinDto cli2 = new ClientJoinDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, "무직",LocalDate.now(), interestList);

        clientService.signUp(cli1);

//        assertThat(clientService.getClientsCount()).isEqualTo(1);
        clientService.signUp(cli2);

//        assertThat(clientService.getClientsCount()).isEqualTo(2);

    }

    @Test
    public void joinFail(){
        List<String> interestList = List.of("영어", "독일어", "북아프리카");
        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, "무직", LocalDate.now(), interestList);
        ClientJoinDto cli2 = new ClientJoinDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, "무직",LocalDate.now(), interestList);

        clientService.signUp(cli1);

        Assertions.assertThrows(ExistEmailException.class,() ->clientService.signUp(cli2));
    }
    @Test
    public void basicCRUD(){
        //코드 수정해야힘
        List<String> interestList = List.of("영어", "독일어", "북아프리카");
        ClientJoinDto cli1 = new ClientJoinDto("123","4321","ha","bob","spqjf", Client.Gender.F, "무직", LocalDate.now(), interestList);
        ClientJoinDto cli2 = new ClientJoinDto("124","4321","sim","sdfs","sdfsdgs", Client.Gender.F, "무직",LocalDate.now(), interestList);

        clientService.signUp(cli1);
        clientService.signUp(cli2);


//        ClientResponseDto findCli = clientService.findById(cli1.getUsername());
//
//        assertThat(findCli.getNickname()).isEqualTo("sdgsdf");
//
//        assertThat(clientService.getClientsCount()).isEqualTo(2);



    }
    
}