package kr.KWGraduate.BookPharmacy.global.infra.redis.refreshtoken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {
    //Optional<RefreshToken> findByLoginId(String loginId);
}
