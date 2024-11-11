package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BookRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRecommendRepository extends JpaRepository<BookRecommend, Long> {

    @Query("select br from BookRecommend br join fetch br.recommendingBook b join fetch br.recommendedBook rb where rb.id = :id")
    List<BookRecommend> findByBookBasedRecommend(@Param("id") Long id);
}
