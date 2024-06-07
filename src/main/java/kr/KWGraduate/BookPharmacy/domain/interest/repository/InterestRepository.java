package kr.KWGraduate.BookPharmacy.domain.interest.repository;

import kr.KWGraduate.BookPharmacy.domain.interest.domain.Interest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Long> {


    // 유저의 아이디로 관심사를 조회함
    @EntityGraph(attributePaths = {"category"})
    @Query("select i from Interest i join fetch i.client c where c.loginId = :loginId")
    List<Interest> findByLoginId(@Param("loginId") String loginId);
}
