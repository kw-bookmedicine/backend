package kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository;

import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLineHelpfulEmotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OneLineHelpfulEmotionRepository extends JpaRepository<OneLineHelpfulEmotion, Long> {

    // 유저가 '도움이 되었어요'를 눌렀는지 확인하기 위함
    @Query("select oh from OneLineHelpfulEmotion oh join fetch oh.client c where c.loginId = :loginId")
    OneLineHelpfulEmotion findByLoginId(@Param("loginId") String loginId);

    @Query("select oh from OneLineHelpfulEmotion oh join fetch oh.client c join fetch oh.oneLinePrescription op " +
            "where c.loginId = :loginId " +
            "and op.id = :oneLineId")
    Optional<OneLineHelpfulEmotion> findByLoginIdAndOneLinePreId(@Param("loginId") String loginId, @Param("oneLineId") Long oneLineId);

    @Query("select count(oh) from OneLineHelpfulEmotion oh inner join oh.oneLinePrescription op where op.id = :oneLineId")
    long findCountByOneLinePreId(@Param("oneLineId") Long oneLineId);

}
