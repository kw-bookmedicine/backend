package kr.KWGraduate.BookPharmacy.domain.client.service;

import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistEmailException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistIdException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistNicknameException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.NoExistIdException;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientResponseDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.global.common.error.AllException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public ClientResponseDto getClient(){
        String username = getUsername();

        Client client = clientRepository.findByLoginId(username).get();
        return ClientResponseDto.toDto(client);
    }

    @Transactional
    public void cancellation(){
        String username = getUsername();
        clientRepository.deleteByLoginId(username);
    }
    @Transactional
    public void removeClient(Long id){
        clientRepository.deleteById(id);
    }

    public ClientResponseDto findById(String id){
        return clientRepository.findById(id)
                .map(ClientResponseDto::toDto)
                .orElseThrow(() -> new NoExistIdException("id 없음"));
    }
    public List<ClientResponseDto> findAll(){
        return clientRepository.findAll().stream()
                .map(ClientResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public Long getClientsCount(){
        return clientRepository.count();
    }

    private String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthenticationAdapter principal = (AuthenticationAdapter)authentication.getPrincipal();
        return principal.getUsername();
    }
}
