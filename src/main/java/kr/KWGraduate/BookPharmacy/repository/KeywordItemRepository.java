package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.dto.keyword.BookKeywordDTO;
import kr.KWGraduate.BookPharmacy.dto.keyword.ClientKeywordDTO;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.KeywordItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordItemRepository extends JpaRepository<KeywordItem, Long> {

    //book 관련 쿼리

    @Query(value = "select k from KeywordItem k join fetch k.bookKeywords bk join fetch bk.book b where b.isbn = :isbn")
    List<KeywordItem> findByIsbn(@Param("isbn") String isbn);


    //client 관련 쿼리
    @Query("select k from KeywordItem k join fetch k.clientKeywords ck join fetch ck.client c where c.id = :id")
    List<KeywordItem> findByClientId(@Param("id") Long id);

    //인기

}
