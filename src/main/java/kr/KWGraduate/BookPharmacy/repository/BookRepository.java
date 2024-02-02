package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    Optional<Book> findByTitleAndAuthorAndPublishYear(String title, String author, String publishYear);

    Optional<Book> findOptionalByIsbn(String isbn);

    /**
     * 내가 읽은 책 조회 (성능개선 필요, 일대다인데 페치조인)
     */
    @Query("select b from Book b join fetch b.feeds f join fetch f.client c where c.id = :userId")
    List<Book> findByUserId(@Param("userId") Long userId);

    /**
     * 키워드로 책 조회
     */
    @Query("select b from Book b join fetch b.bookKeywords bk join fetch bk.keywordItem k where k.name = :keyword")
    List<Book> findByKeywordName(@Param("keyword") String keywordName);

    /**
     * 중분류로 책 조회
     */
    @Query("select b from Book b join fetch b.middleCategory mc where mc.name = :categoryName")
    List<Book> findByMiddleCategory(@Param("categoryName") String categoryName);

    /**
     * 중분류로 책 조회 ( 페이징 )
     */
    @Query(value = "select b from Book b inner join b.middleCategory mc join fetch b.bigCategory bc where mc.name = :categoryName")
    Page<Book> findBookPagingByMiddleCategory(@Param("categoryName") String categoryName, Pageable pageable);

    /**
     * 대분류로 책 조회 (대분류로 조회할 경우, 중분류와 함께 리스트로 출력하므로 fetch join을 걸었음)
     * 다만, Map<중분류, List<Book>> 으로 나타내는 것을 DB조회에서 한번에 처리할지 or 조회 후 따로 처리할지 고민해봐야 할 듯
     */
    @Query("select b from Book b join fetch b.bigCategory bc join fetch b.middleCategory mc where bc.name = :categoryName")
    List<Book> findByBigCategory(@Param("categoryName") String categoryName);

    /**
     * 검색어로 책 조회
     * @param searchKeyword
     * @return List<Book>
     */
    List<Book> findByTitleContaining(String searchKeyword);

    /**
     * 검색어로 책 조회 (페이징) (Service 레이어에서 BookSearchDto로 전환)
     * @param searchKeyword
     * @return List<Book>
     */
    Page<Book> findPagingByTitleContaining(String searchKeyword, Pageable pageable);
}
