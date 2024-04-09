package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Prescription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("select p from Prescription p join fetch p.board b where b.id = :id")
    Slice<Prescription> findByBoardId(Pageable pageable, @Param("id") Long boardId);

    @Query("select p from Prescription p join fetch p.client c where c.loginId = :loginId")
    Slice<Prescription> findByUsername(Pageable pageable, @Param("loginId") String username);


    @Query("select b.id, count(p) from Prescription p join p.board b where b.id in :boardIds group by b.id")
    List<Object[]> countByBoard(@Param("boardIds") List<Long> boardIds);


    void deleteById(Long id);
}
