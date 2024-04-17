package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.ReadExperience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReadExperienceRepository extends JpaRepository<ReadExperience, Long> {


    /**
     * 유저 정보로 읽은 경험을 모두 조회하기
     * (머신러닝 과정)에서 사용
     */
    @EntityGraph(attributePaths = {"book"})
    @Query("select re from ReadExperience re join fetch re.client c where c.loginId = :loginId")
    List<ReadExperience> findByLoginId(@Param("loginId") String clientLoginId);

    /**
     * 유저 정보로 읽은 경험을 모두 조회하기 (페이징)
     * (나의 독서 경험 관리하기)에서 사용
     */
    @EntityGraph(attributePaths = {"book"})
    @Query("select re from ReadExperience re join fetch re.client c where c.loginId = :loginId")
    Page<ReadExperience> findPagingByLoginId(@Param("loginId") String clientLoginId, Pageable pageable);

    /**
     * 유저 정보와 책 정보로 조회하기
     * (나의 독서 경험 관리하기)에서 사용
     */
    @Query("select re from ReadExperience re join fetch re.client c join fetch re.book b" +
            " where c.loginId = :loginId and b.isbn = :bookIsbn")
    Optional<ReadExperience> findByLoginIdAndBookIsbn(@Param("loginId") String clientLoginId, @Param("bookIsbn") String bookIsbn);

    /**
     * 책 isbn으로 읽은 경험 조회하기
     * (이후에 있을 머신러닝에서 사용 : isbn으로 이 책을 읽은 사람들 모두 조회 -> 그 사람들의 id로 독서경험을 모두 조회)
     */
    @EntityGraph(attributePaths = {"client"})
    @Query("select re from ReadExperience re join fetch re.book b where b.isbn = :isbn")
    List<ReadExperience> findByBookIsbn(@Param("isbn") String bookIsbn);


}
