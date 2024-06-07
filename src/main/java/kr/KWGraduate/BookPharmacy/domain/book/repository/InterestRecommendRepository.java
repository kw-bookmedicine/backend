package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.InterestRecommend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestRecommendRepository extends JpaRepository<InterestRecommend, Long> {

    @Query(value = "select ir.* from book_pharmacy_local.interest_recommend as ir " +
            "inner join book_pharmacy_local.book as b on ir.book_id = b.book_id " +
            "order by RAND()", nativeQuery = true)
    List<InterestRecommend> findRandAll(Pageable pageable);

    @Query(value = "select ir.* from book_pharmacy_local.interest_recommend as ir " +
            "inner join book_pharmacy_local.book as b on ir.book_id = b.book_id " +
            "inner join book_pharmacy_local.categories as c on ir.category_id = c.category_id " +
            "where c.category_id in :categoryIdList order by RAND()"
    , nativeQuery = true)
    List<InterestRecommend> findByInterestList(@Param("categoryIdList") List<Long> categoryIdList, Pageable pageable);
}
    