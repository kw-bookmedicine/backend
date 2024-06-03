package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.BestSellerBook;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BestSellerBookRepository extends JpaRepository<BestSellerBook, Long> {

    @EntityGraph(attributePaths = {"book"})
    @Query("SELECT b FROM BestSellerBook b")
    List<BestSellerBook> findAllBestSeller();
}
