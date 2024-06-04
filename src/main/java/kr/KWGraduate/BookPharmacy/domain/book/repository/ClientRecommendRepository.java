package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRecommendRepository extends JpaRepository<ClientRecommend ,Long> {
    @Query("select cr from ClientRecommend cr join fetch cr.book b where cr.client.id = :clientId and cr.rank >= 7")
    List<ClientRecommend> findByClientBasedRecommend(@Param("clientId") Long clientId);

    @Query("select cr from ClientRecommend cr join fetch cr.book b where cr.client.id = :clientId and cr.rank < 7")
    List<ClientRecommend> findByClientAiPrescription(@Param("clientId") Long clientId);
}
