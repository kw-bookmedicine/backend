package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRecommendRepository extends JpaRepository<ClientRecommend ,Long> {
}
