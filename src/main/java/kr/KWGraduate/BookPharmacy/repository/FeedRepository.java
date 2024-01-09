package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    /**
     * 유저의 Id로 feed 조회하기
     * */
    @Query("select f from Feed f join fetch f.clientBook cb join fetch cb.client c where c.id = :clientId")
    List<Feed> findByClientId(@Param("clientId") String clientId);

    /**
     * 책 Id로 feed 조회하기
     * */
    @Query("select f from Feed f join fetch f.clientBook cb join fetch cb.book b where b.id = :bookId")
    List<Feed> findByBookId(@Param("bookId") String bookId);

    /**
     * 모든 피드를 등록된 순서대로 페이징해서 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.clientBook cb join fetch cb.client c join fetch cb.book b")
    Page<Feed> findPagingAndSorting(Pageable pageable);

    /**
     * 책 Id에 대하여, 등록된 날짜 순서대로 페이징해서 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.clientBook cb join fetch cb.client c join fetch cb.book b where b.id = :bookId")
    Page<Feed> findPagingByBookId(@Param("bookId") Long bookId, Pageable pageable);
}
