package kr.KWGraduate.BookPharmacy.domain.keyworditem.repository;

import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.KeywordItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordItemRepository extends JpaRepository<KeywordItem, Long> {

    //book 관련 쿼리

    @Query(value = "select k from KeywordItem k join fetch k.bookKeywords bk join fetch bk.book b where b.id = :bookId")
    List<KeywordItem> findByBookId(@Param("bookId") Long bookId);


    //client 관련 쿼리
    @Query("select k from KeywordItem k join fetch k.clientKeywords ck join fetch ck.client c where c.id = :id")
    List<KeywordItem> findByClientId(@Param("id") Long id);

    //인기


    // 검색어를 이름에 포함하는 키워드 목록 불러오기
    @Query("SELECT k FROM KeywordItem k WHERE LOWER(k.name) LIKE %:searchWord%")
    Page<KeywordItem> findByNameContaining(@Param("searchWord") String searchWord, Pageable pageable);
}
