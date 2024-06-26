package kr.KWGraduate.BookPharmacy.global.security.auth.service;

import kr.KWGraduate.BookPharmacy.global.security.auth.dto.ClientDetails;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByLoginId(username)
                .map(ClientDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("can not find username"));
    }
}
