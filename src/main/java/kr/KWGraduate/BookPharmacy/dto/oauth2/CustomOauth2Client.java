package kr.KWGraduate.BookPharmacy.dto.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@ToString
public class CustomOauth2Client implements OAuth2User {

    private final Oauth2ClientDto oauth2ClientDto;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return oauth2ClientDto.getRole();
            }
        });

        return collection;
    }
    @Override
    public String getName() {
        return oauth2ClientDto.getName();
    }

    public String getUsername(){
        return oauth2ClientDto.getUsername();
    }
}
