package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Board;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
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
    @Query("select b from Board b join fetch b.client c where (b.title like %:searchKeyword% or b.description like %:searchKeyword%) and b.status = kr.KWGraduate.BookPharmacy.enums.Status.PRESCRIBING")
    Slice<Board> findByTitleContainingOrDescriptionContaining(Pageable pageable,@Param("searchKeyword") String searchKeyword);

    @Query("select b from Board b join fetch b.client c where c.loginId = :loginId")
    Slice<Board> findByUsername(Pageable pageable, @Param("loginId") String username);


    @Query("select b from Board b join fetch b.client c where c.loginId = :loginId and b.status = :status")
    Slice<Board> findByUsernameAndStatus(Pageable pageable, @Param("loginId") String username, @Param("status")Status status);

    void deleteById(Long id);
}
