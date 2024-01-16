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
     * 유저의 Id로 모두 조회하기
     * */
    @Query("select f from Feed f join fetch f.book b join fetch f.client c where c.id = :clientId")
    List<Feed> findByClientId(@Param("clientId") String clientId);

    /**
     * 유저의 Id에 대해 평가를 남긴 피드만 조회하기
     * */
    @Query("select f from Feed f join fetch f.book b join fetch f.client c where c.id = :clientId and f.isRated = true")
    List<Feed> findRatedFeedsByClientId(@Param("clientId") String clientId);

    /**
     * 책 Id에 대해 평가를 남긴 피드만 조회하기
     * */
    @Query("select f from Feed f join fetch f.book b join fetch f.client c where b.id = :bookId and f.isRated = true")
    List<Feed> findByBookId(@Param("bookId") String bookId);

    /**
     * 모든 피드를 페이징해서 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.client c join fetch f.book b where f.isRated = true")
    Page<Feed> findPagingAndSorting(Pageable pageable);

    /**
     * 유저의 Id에 대하여 페이징해서 모두 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.book b join fetch f.client c where c.id = :clientId")
    Page<Feed> findPagingByClientId(@Param("clientId") String clientId, Pageable pageable);

    /**
     * 유저의 Id에 대하여 평가를 남긴 피드만 페이징해서 모두 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.book b join fetch f.client c where c.id = :clientId and f.isRated = true")
    Page<Feed> findRatedFeedsPagingByClientId(@Param("clientId") String clientId, Pageable pageable);

    /**
     * 책 Id에 대하여 평가를 남긴 피드만 페이징해서 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.client c join fetch f.book b where b.id = :bookId and f.isRated = true")
    Page<Feed> findPagingByBookId(@Param("bookId") Long bookId, Pageable pageable);


}
