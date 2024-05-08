package kr.KWGraduate.BookPharmacy.global.infra.redis;

import kr.KWGraduate.BookPharmacy.global.security.common.dto.TokenDto;
import kr.KWGraduate.BookPharmacy.domain.client.exception.NoExistIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository tokenRepository;

    public void save(TokenDto token,String username){
        RefreshToken refreshToken = RefreshToken.builder()
                .loginId(username)
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .isLogout(false)
                .build();

        tokenRepository.save(refreshToken);
    }
    public void save(RefreshToken refreshToken){
        tokenRepository.save(refreshToken);
    }
    public RefreshToken findByUsername(String username){
        return tokenRepository.findById(username)
                .orElseThrow(() -> new NoExistIdException("there is no id"));

    }
    public void deleteToken(String username){
        tokenRepository.deleteById(username);
    }
}
