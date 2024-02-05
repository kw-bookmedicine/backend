package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,String> {

    @Query("select m from Client m where m.nickname = :nickname")
    Optional<Client> findByNickname(@Param("nickname") String nickname);

    @Query("select m from Client m where m.email = :email")
    Optional<Client> findByEmail(@Param("email") String email);

    Optional<Client> findByLoginId(@Param("loginId") String loginId);

    Optional<Client> findById(@Param("id")Long id);

    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String email);
    Boolean existsByLoginId(String loginId);

    void deleteById(@Param("id")Long id);
}
