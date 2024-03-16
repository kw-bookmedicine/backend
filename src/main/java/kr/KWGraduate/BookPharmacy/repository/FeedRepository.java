package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    /**
     * 유저의 Id로 모두 조회하기
     * */
    @Query("select f from Feed f join fetch f.book b join fetch f.client c where c.loginId = :loginId")
    List<Feed> findByLoginId(@Param("loginId") String loginId);

    /**
     * 유저의 Id에 대해 평가를 남긴 피드만 조회하기
     * */
    @Query("select f from Feed f join fetch f.book b join fetch f.client c where c.loginId = :loginId and f.isRated = true")
    List<Feed> findRatedFeedsByLoginId(@Param("loginId") String loginId);

    /**
     * 책 isbn에 대해 평가를 남긴 피드만 조회하기
     * */
    @Query("select f from Feed f join fetch f.book b join fetch f.client c where b.isbn = :isbn and f.isRated = true")
    List<Feed> findByBookIsbn(@Param("isbn") String isbn);

    /**
     * 모든 피드를 페이징해서 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.client c join fetch f.book b where f.isRated = true")
    Page<Feed> findPagingAndSorting(Pageable pageable);

    /**
     * 유저의 Id에 대하여 페이징해서 모두 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.book b join fetch f.client c where c.loginId = :loginId")
    Page<Feed> findPagingByLoginId(@Param("loginId") String loginId, Pageable pageable);

    /**
     * 유저의 Id에 대하여 평가를 남긴 피드만 페이징해서 모두 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.book b join fetch f.client c where c.loginId = :loginId and f.isRated = true")
    Page<Feed> findRatedFeedsPagingByLoginId(@Param("loginId") String loginId, Pageable pageable);

    /**
     * 책 isbn에 대하여 평가를 남긴 피드만 페이징해서 조회하기
     * */
    @Query(value = "select f from Feed f join fetch f.client c join fetch f.book b where b.isbn = :isbn and f.isRated = true")
    Page<Feed> findPagingByBookIsbn(@Param("isbn") String isbn, Pageable pageable);

    /**
     * 유저의 Id와 책 Id에 대하여 피드를 조회하기 (단건만 조회되어야 함) (평가를 남겼는지 안남겼는지는 모름)
     */
    @Query(value = "select f from Feed f join fetch f.client c join fetch f.book b where c.loginId = :loginId and b.isbn = :isbn")
    Optional<Feed> findOptionalByLoginIdAndIsbn(@Param("loginId") String loginId, @Param("isbn") String isbn);
}
