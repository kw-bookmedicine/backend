package kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2;

import kr.KWGraduate.BookPharmacy.domain.client.exception.NoExistIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class Oauth2SignUpService {
    private final Oauth2SignUpRepository oauth2SignUpRepository;

    public void save(String providerId, String email, String name){
        Oauth2SignUpInfo info = Oauth2SignUpInfo.builder()
                .email(email)
                .name(name)
                .providerId(providerId)
                .build();
        oauth2SignUpRepository.save(info);
    }

    public Oauth2SignUpInfo getSignUpInfo(String email){
        return oauth2SignUpRepository.findById(email).orElseThrow(() -> new NoExistIdException("there is no id"));
    }

    public void deleteInfo(String email){
        oauth2SignUpRepository.deleteById(email);
    }
}
