package kr.KWGraduate.BookPharmacy.domain.client.service;

import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientMainPageDto;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistEmailException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistIdException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistNicknameException;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientMypageDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.global.common.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(ClientJoinDto clientJoinDto) throws BusinessException {
        int passwordLength = clientJoinDto.getPassword().length();
        clientJoinDto.setPassword(bCryptPasswordEncoder.encode(clientJoinDto.getPassword()));
        Client client = clientJoinDto.toEntity(passwordLength);
        validateDuplicateClient(client);

        clientRepository.save(client);
    }

    private void validateDuplicateClient(Client client) {

        if(clientRepository.existsByLoginId(client.getLoginId())){
            throw new ExistIdException(client.getLoginId());
        }
        if(clientRepository.existsByNickname(client.getNickname())){
            throw new ExistNicknameException(client.getNickname());
        }
        if(clientRepository.existsByEmail(client.getEmail())){
            throw new ExistEmailException(client.getEmail());
        }
    }
    @Transactional
    public void updateClient(ClientUpdateDto clientUpdateDto, AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();

        Client client = clientRepository.findByLoginId(username).get();
        //clientUpdateDto.setPassword((bCryptPasswordEncoder.encode(clientUpdateDto.getPassword())));
        client.update(clientUpdateDto);
    }
    @Transactional
    public void updatePassword(String password,AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();
        Client client = clientRepository.findByLoginId(username).get();
        String encodingPassword = bCryptPasswordEncoder.encode(password);
        client.setPassword(encodingPassword);
    }

    @Transactional
    public void updateNickname(String nickname, AuthenticationAdapter authenticationAdapter) {
        String username = authenticationAdapter.getUsername();
        Client client = clientRepository.findByLoginId(username).get();
        client.setNickname(nickname);
    }

    public ClientMainPageDto getMainPageClient(AuthenticationAdapter authenticationAdapter) {
        String username = authenticationAdapter.getUsername();
        Client client = clientRepository.findByLoginId(username).get();

        return new ClientMainPageDto(client.getNickname());
    }
    public ClientMypageDto getClient(AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();

        Client client = clientRepository.findByLoginId(username).get();
        return ClientMypageDto.toDto(client);
    }

    @Transactional
    public void cancellation(AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();
        clientRepository.deleteByLoginId(username);
    }
    public boolean isExistId(String username){
        return clientRepository.existsByLoginId(username);
    }
    public boolean isExistNickname(String nickname){
        return clientRepository.existsByNickname(nickname);
    }
    public boolean isExistEmail(String email){
        return clientRepository.existsByEmail(email);
    }
}
