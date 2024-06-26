package kr.KWGraduate.BookPharmacy.global.security.oauth.service;

import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.CustomOauth2Client;
import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.NaverResponse;
import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.Oauth2ClientDto;
import kr.KWGraduate.BookPharmacy.global.security.oauth.dto.Oauth2Response;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Oauth2ClientService extends DefaultOAuth2UserService {

    private final ClientRepository clientRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Oauth2Response oauth2Response = null;

        if (registrationId.equals("naver")) {
            oauth2Response = new NaverResponse(oauth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            //oauth2Response = new GoogleResponse(oauth2User.getAttributes());
        }else {
            return null;
        }

        String username = oauth2Response.getProvider() + " " + oauth2Response.getProviderId();
        String name = oauth2Response.getName();
        String email = oauth2Response.getEmail();

        Oauth2ClientDto oauth2ClientDto = getOauth2ClientDto(username, name, email);

        return new CustomOauth2Client(oauth2ClientDto);
    }

    private Oauth2ClientDto getOauth2ClientDto(String username, String name, String email){
        return clientRepository.findByLoginId(username)
                .map(client -> Oauth2ClientDto.builder()
                            .email(client.getEmail())
                            .username(client.getLoginId())
                            .name(client.getName())
                            .role(client.getRole())
                            .isExist(true)
                            .build())
                .orElseGet(() -> Oauth2ClientDto.builder()
                        .email(email)
                        .username(username)
                        .name(name)
                        .isExist(false)
                        .build());
    }
}
