package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.ClientDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.exception.status.*;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public ClientJoinDto signUp(ClientJoinDto clientJoinDto) throws AllException {
        Client client = clientJoinDto.toEntity();
        validateDuplicateClient(client);

        return ClientJoinDto.toDto(clientRepository.save(client));

    }

    private void validateDuplicateClient(Client client) {


        if(isExistLoginId(client.getLoginId())){
            throw new ExistIdException("이미 id가 존재합니다.");
        }
        if(isExistNickname(client.getNickname())){
            throw new ExistNicknameException("이미 존재하는 닉네임입니다.");
        }
        if(isExistEmail(client.getEmail())){
            throw new ExistEmailException("이미 존재하는 이메일입니다.");
        }
    }

    public boolean isExistLoginId(String loginId){
        return clientRepository.existsByLoginId(loginId);
    }
    public boolean isExistNickname(String nickname){ return clientRepository.existsByNickname(nickname); }
    public boolean isExistEmail(String email){
        return clientRepository.existsByEmail(email);
    }

    public ClientLoginDto Login(String loginId, String password){
        //예외를 돌리는 것이 나은지

        Client client = clientRepository.findByLoginId(loginId).orElseThrow(() -> new NoExistIdException("id가 존재하지 않습니다."));

        if(client.isEqualPassword(password)){
            return new ClientLoginDto(client.getLoginId(), client.getPassword());
        }
        else{
            throw new IsNotSamePasswordException("password가 일치하지 않습니다.");
        }
    }
    @Transactional
    public void removeClient(Long id){
        clientRepository.deleteById(id);
    }

    //여기서부터는 아직 쓰이지 않는 메소드
    //book관련과 키워드 관련메소드도 생성해야함
    //코드 수정해야함

    public Client findById(String id){
        return clientRepository.findById(id).orElseThrow(() ->new NoSuchElementException());
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
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
        return client.getLoginId();
    }




}
