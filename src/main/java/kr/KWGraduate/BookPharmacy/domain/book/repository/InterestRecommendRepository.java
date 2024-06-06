package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.InterestRecommend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestRecommendRepository extends JpaRepository<InterestRecommend, Long> {

    @EntityGraph(attributePaths = {"book"})
    @Query("select ir from InterestRecommend ir join fetch ir.middleCategory mc where mc.id in :categoryIdList")
    List<InterestRecommend> findByInterestList(@Param("categoryIdList") List<Long> categoryIdList, Pageable pageable);
}
    