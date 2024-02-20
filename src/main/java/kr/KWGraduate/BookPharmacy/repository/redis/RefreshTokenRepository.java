package kr.KWGraduate.BookPharmacy.repository.redis;

import kr.KWGraduate.BookPharmacy.entity.redis.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
    //Optional<RefreshToken> findByLoginId(String loginId);
}
