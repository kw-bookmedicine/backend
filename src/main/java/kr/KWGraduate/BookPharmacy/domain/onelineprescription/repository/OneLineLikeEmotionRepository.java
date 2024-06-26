package kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository;

import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLineLikeEmotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OneLineLikeEmotionRepository extends JpaRepository<OneLineLikeEmotion, Long> {

    @Query("select ol from OneLineLikeEmotion ol join fetch ol.client c join fetch ol.oneLinePrescription op " +
            "where c.loginId = :loginId " +
            "and op.id = :oneLineId")
    Optional<OneLineLikeEmotion> findByLoginIdAndOneLinePreId(@Param("loginId") String loginId, @Param("oneLineId") Long oneLineId);

    @Query("select count(ol) from OneLineLikeEmotion ol inner join ol.oneLinePrescription op where op.id = :oneLineId")
    long findCountByOneLinePreId(@Param("oneLineId") Long oneLineId);

}
