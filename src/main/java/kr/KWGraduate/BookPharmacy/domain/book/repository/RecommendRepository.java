package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.repository.BoardBasedRecommendRepositoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Book,Long> {

    /*
     * 연관 책 리스트 가져오는 쿼리
     * */
    @Query("select b from Book b join fetch b.bookRecommends br where br.recommendedBook.isbn = :isbn")
    List<Book> findByBookBasedRecommend(@Param("isbn") String isbn);

    /*
     * 고민 게시판 ai처방
     * */
    @Query("select new kr.KWGraduate.BookPharmacy.domain.book.dto.repository.BoardBasedRecommendRepositoryDto(b,br.keywords) from Book b join fetch b.boardRecommends br where br.board.id = :boardId")
    Optional<BoardBasedRecommendRepositoryDto> findByBoardBasedRecommend(@Param("boardId") Long boardId);

    /*
     * 메인 페이지 나와 유사한 유저들을 기반하는 책 추천가져오는 쿼리
     * */
    @Query("select b from Book b join fetch b.clientRecommends cr where cr.client.id = :clientId and cr.rank < 3")
    List<Book> findByClientAiPrescription(@Param("clientId") Long clientId);

    @Query("select b from Book b join fetch b.clientRecommends cr where cr.client.id = :clientId and cr.rank >= 3")
    List<Book> findByClientBasedRecommend(@Param("clientId") Long clientId);
}
