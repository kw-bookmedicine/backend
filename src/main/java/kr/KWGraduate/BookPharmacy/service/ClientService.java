package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.client.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.dto.client.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientResponseDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientUpdateDto;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.exception.status.*;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(ClientJoinDto clientJoinDto) throws AllException {
        clientJoinDto.setPassword(bCryptPasswordEncoder.encode(clientJoinDto.getPassword()));
        Client client = clientJoinDto.toEntity();
        validateDuplicateClient(client);

        clientRepository.save(client);
    }

    private void validateDuplicateClient(Client client) {

        if(clientRepository.existsByLoginId(client.getLoginId())){
            throw new ExistIdException("이미 id가 존재합니다.");
        }
        if(clientRepository.existsByNickname(client.getNickname())){
            throw new ExistNicknameException("이미 존재하는 닉네임입니다.");
        }
        if(clientRepository.existsByEmail(client.getEmail())){
            throw new ExistEmailException("이미 존재하는 이메일입니다.");
        }
    }
    @Transactional
    public void updateClient(ClientUpdateDto clientUpdateDto){
        String username = getUsername();

        Client client = clientRepository.findByLoginId(username).get();
        clientUpdateDto.setPassword((bCryptPasswordEncoder.encode(clientUpdateDto.getPassword())));

        client.update(clientUpdateDto);

    }


    @Transactional
    public void removeClient(Long id){
        clientRepository.deleteById(id);
    }

    public Client findById(String id){
        return clientRepository.findById(id).orElseThrow(() -> new NoExistIdException("id가 존재하지 않음"));
    }
    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Long getClientsCount(){
        return clientRepository.count();
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationAdapter principal = (AuthenticationAdapter)authentication.getPrincipal();
        String username = principal.getUsername();
        return username;
    }
}
