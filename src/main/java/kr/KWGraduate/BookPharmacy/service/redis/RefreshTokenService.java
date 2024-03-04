package kr.KWGraduate.BookPharmacy.service.redis;

import kr.KWGraduate.BookPharmacy.dto.token.TokenDto;
import kr.KWGraduate.BookPharmacy.entity.redis.RefreshToken;
import kr.KWGraduate.BookPharmacy.exception.status.NoExistIdException;
import kr.KWGraduate.BookPharmacy.repository.redis.RefreshTokenRepository;
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
