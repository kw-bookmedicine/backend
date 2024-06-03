package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.board.repository.BoardRepository;
import kr.KWGraduate.BookPharmacy.domain.book.domain.BoardRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRecommendRepository extends JpaRepository<BoardRecommend, Long> {

    @Query("select br from BoardRecommend br join fetch br.book b where br.board.id = :boardId")
    Optional<BoardRecommend> findByBoardBasedRecommend(@Param("boardId") Long boardId);
}
