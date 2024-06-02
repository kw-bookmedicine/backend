package kr.KWGraduate.BookPharmacy.domain.client.service;

import kr.KWGraduate.BookPharmacy.domain.client.dto.request.*;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientMainPageDto;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistEmailException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistIdException;
import kr.KWGraduate.BookPharmacy.domain.client.exception.ExistNicknameException;
import kr.KWGraduate.BookPharmacy.domain.interest.repository.InterestRepository;
import kr.KWGraduate.BookPharmacy.domain.interest.service.InterestService;
import kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2.Oauth2SignUpInfo;
import kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2.Oauth2SignUpService;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientMypageDto;
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
    private final ClientOccupationMapService clientOccupationMapService;
    private final InterestService interestService;
    private final Oauth2SignUpService oauth2SignUpService;

    @Transactional
    public void signUp(ClientJoinDto clientJoinDto) throws BusinessException {
        int passwordLength = clientJoinDto.getPassword().length();
        clientJoinDto.setPassword(bCryptPasswordEncoder.encode(clientJoinDto.getPassword()));

        Client.Occupation occupation = clientOccupationMapService.getValue(clientJoinDto.getOccupation());
        Client client = clientJoinDto.toEntity(passwordLength,occupation);
        validateDuplicateClient(client);

        Client savedClient = clientRepository.save(client);
        interestService.updateInterest(clientJoinDto.getInterestList(), savedClient);
    }
    @Transactional
    public void signUp(String email, ClientOauthJoinDto clientOauthJoinDto){
        Oauth2SignUpInfo signUpInfo = oauth2SignUpService.getSignUpInfo(email);
        String password = bCryptPasswordEncoder.encode(signUpInfo.getPassword());
        Client.Occupation occupation = clientOccupationMapService.getValue(clientOauthJoinDto.getOccupation());

        Client client = clientOauthJoinDto.toEntity(signUpInfo, occupation, password);
        validateDuplicateClient(client);

        Client savedClient = clientRepository.save(client);
        interestService.updateInterest(clientOauthJoinDto.getInterestList(), savedClient);

        oauth2SignUpService.deleteInfo(email);
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

        Client.Occupation occupation = clientOccupationMapService.getValue(clientUpdateDto.getOccupation());
        client.update(occupation,clientUpdateDto.getDescription());
    }
    @Transactional
    public void updatePassword(ClientPasswordUpdateDto clientPasswordUpdateDto, AuthenticationAdapter authenticationAdapter){
        String username = authenticationAdapter.getUsername();
        String password = clientPasswordUpdateDto.getPassword();

        Client client = clientRepository.findByLoginId(username).get();
        String encodingPassword = bCryptPasswordEncoder.encode(password);
        client.setPassword(encodingPassword,password.length());
    }

    @Transactional
    public void updateNickname(ClientNicknameDto clientNicknameDto, AuthenticationAdapter authenticationAdapter) {
        String username = authenticationAdapter.getUsername();
        String nickname = clientNicknameDto.getNickname();

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

        return clientRepository.findByLoginId(username)
                .map(ClientMypageDto::new).get();
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
