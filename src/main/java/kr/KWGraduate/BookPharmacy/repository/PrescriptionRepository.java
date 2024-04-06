package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Prescription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("select p from Prescription p join fetch p.board b where b.id = :id")
    Slice<Prescription> findByBoardId(Pageable pageable, @Param("id") Long boardId);

    @Query("select p from Prescription p join fetch p.client c where c.id = :id")
    Slice<Prescription> findByClientId(Pageable pageable, @Param("id") Long clientId);


    @Query("select count(p) from Prescription p  where p.board.id = :id")
    long countByBoard(@Param("id") Long boardId);

    void deleteById(Long id);
}
