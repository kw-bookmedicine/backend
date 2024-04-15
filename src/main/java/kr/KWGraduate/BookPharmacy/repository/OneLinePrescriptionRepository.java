package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Keyword;
import kr.KWGraduate.BookPharmacy.entity.OneLinePrescription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OneLinePrescriptionRepository extends JpaRepository<OneLinePrescription, Long> {

    /**
     * 제목 또는 설명에 '검색어'를 포함하는 한줄처방 목록 조회
     * */
    @EntityGraph(attributePaths = {"client", "book"})
    @Query("select op from OneLinePrescription op where (op.title like %:searchWord% or op.description like %:searchWord%)")
    List<OneLinePrescription> findByTitleOrDescriptionContaining(@Param("searchWord") String searchWord);

    /**
     * 책의 isbn에 해당하는 한줄처방 목록 조회
     * */
    @EntityGraph(attributePaths = {"client"})
    @Query("select op from OneLinePrescription op join fetch op.book b where b.isbn = :isbn")
    List<OneLinePrescription> findByBookTitle(@Param("isbn") String bookIsbn);

    /**
     * 한줄처방의 키워드에 해당하는 한줄처방 목록 조회
     * */
    @EntityGraph(attributePaths = {"client", "book"})
    List<OneLinePrescription> findByKeyword(Keyword keyword);
}
