package kr.KWGraduate.BookPharmacy.domain.prescription.repository;

import kr.KWGraduate.BookPharmacy.domain.prescription.domain.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("select p from Prescription p join fetch p.board b where b.id = :id")
    Page<Prescription> findByBoardId(Pageable pageable, @Param("id") Long boardId);

    @Query("select p from Prescription p join fetch p.client c where c.loginId = :loginId")
    Page<Prescription> findByUsername(Pageable pageable, @Param("loginId") String username);


    @Query("select p.board.id, count(p) from Prescription p where p.board.id in :boardIds group by p.board.id")
    List<Object[]> countByBoard(@Param("boardIds") List<Long> boardIds);

    void deleteById(Long id);
}
