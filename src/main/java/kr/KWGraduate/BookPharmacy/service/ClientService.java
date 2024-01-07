package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.ClientDto;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository_test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public boolean signUp(ClientDto clientDto){
        Client client = clientDto.toEntity();

        try{
            validateDuplicateClient(client);
            clientRepository.save(client);
            return true;
        }catch (IllegalArgumentException e){
            e.getMessage();
            return false;
        }

    }



    private void validateDuplicateClient(Client client) {


        if(isExistId(client.getId())){
            throw new IllegalArgumentException("이미 id가 존재합니다.");
        }
        if(isExistNickname(client.getNickname())){
            throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
        }
        if(isExistEmail(client.getEmail())){
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
    }

    public Client findById(String id){
        return clientRepository.findById(id).orElseThrow(() ->new NoSuchElementException());
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    @Transactional
    public void removeClient(String id){
        clientRepository.deleteById(id);
    }

    @Transactional
    public void updateClient(ClientDto clientDto){
        Client findClient = clientRepository.findById(clientDto.getId()).orElseThrow(() -> new NoSuchElementException());

        //optional
        findClient.update(clientDto.getPassword(),clientDto.getNickname(),clientDto.getOccupation());
    }
    public Long getClientsCount(){
        return clientRepository.count();
    }

    public boolean isExistId(String id){
        return clientRepository.findById(id).isPresent();
    }
    public boolean isExistNickname(String nickname){
        return clientRepository.findByNickname(nickname).isPresent();

    }
    public boolean isExistEmail(String email){
        return clientRepository.findByEmail(email).isPresent();
    }

    public boolean canLogin(String id, String password){
        //예외를 돌리는 것이 나은지

        if(isExistId(id)){
            clientRepository.findById(id).get().isEqualPassword(password);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean setVerificationCode(String name, String email, String code){
    // client에 코드 있어야함
        //예외를 호출하는 것이 더 나을 듯?

        if(!isExistEmail(email)){
            return false;
        }
        Client client = clientRepository.findByEmail(email).get();

        if(!client.isEqualName(name)){
            return false;
        }
        //client에 코드 삽입
        return true;
    }
    public String findByCode(String id, String code){
        Client client = clientRepository.findById(id).get();
        //client code비교
        return client.getId();
    }




}
