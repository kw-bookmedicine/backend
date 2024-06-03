package kr.KWGraduate.BookPharmacy.global.infra.redis.oauth2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Oauth2SignUpRepository extends CrudRepository<Oauth2SignUpInfo, String> {
}
