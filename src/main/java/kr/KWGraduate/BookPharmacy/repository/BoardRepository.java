package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.enums.Keyword;
import kr.KWGraduate.BookPharmacy.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("select b from Board b join fetch b.client c where b.status = kr.KWGraduate.BookPharmacy.enums.Status.PRESCRIBING")
    Slice<Board> findAllBoards(Pageable pageable);

    @Query("select b from Board b join fetch b.client c where b.status = kr.KWGraduate.BookPharmacy.enums.Status.PRESCRIBING and b.keyword = :keyword")
    Slice<Board> findByKeyword(Pageable pageable, @Param("keyword")Keyword keyword);

    //검색에 키워드까지 추가
    @Query("select b from Board b join fetch b.client c where (b.title like %:title% or b.description like %:description%) and b.status = kr.KWGraduate.BookPharmacy.enums.Status.PRESCRIBING")
    Slice<Board> findByTitleContainingOrDescriptionContaining(Pageable pageable,@Param("title") String title, @Param("description") String description);

    @Query("select b from Board b join fetch b.client c where c.id = :id")
    Slice<Board> findByClientId(Pageable pageable, @Param("id") Long clientId);


    @Query("select b from Board b join fetch b.client c where c.id = :id and b.status = :status")
    Slice<Board> findByClientIdAndStatus(Pageable pageable, @Param("id") Long clientId, @Param("status")Status status);

    void deleteById(Long id);
}
