package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
    @Query(value = "select b from Book b join fetch b.middleCategory mc join fetch b.bigCategory bc where mc.name = :categoryName")
    Page<Book> findBookPagingByMiddleCategory(@Param("categoryName") String categoryName, Pageable pageable);

    /**
     * 대분류로 책 조회 (대분류로 조회할 경우, 중분류와 함께 리스트로 출력하므로 fetch join을 걸었음)
     * 다만, Map<중분류, List<Book>> 으로 나타내는 것을 DB조회에서 한번에 처리할지 or 조회 후 따로 처리할지 고민해봐야 할 듯
     */
    @Query("select b from Book b join fetch b.bigCategory bc join fetch b.middleCategory mc where bc.name = :categoryName")
    List<Book> findByBigCategory(@Param("categoryName") String categoryName);

    /**
     * 검색어로 제목에 대하여 책 조회
     * @param searchWord
     * @return List<Book>
     */
    @EntityGraph(attributePaths = {"bigCategory", "middleCategory"})
    List<Book> findByTitleContaining(String searchWord);

    /**
     * 검색어로 제목에 대하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @EntityGraph(attributePaths = {"bigCategory", "middleCategory"})
    Page<Book> findPagingByTitleContaining(String searchWord, Pageable pageable);

    /**
     * 검색어로 작가명에 대하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @EntityGraph(attributePaths = {"bigCategory", "middleCategory"})
    Page<Book> findPagingByAuthorContaining(String searchWord, Pageable pageable);

    /**
     * 검색어로 제목과 작가명에 대하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:searchWord% OR LOWER(b.author) LIKE %:searchWord%")
    Page<Book> findPagingByTitleContainingOrAuthorContaining(@Param("searchWord") String searchWord, Pageable pageable);

    List<Book> findAllByIsbnIn(List<String> isbn);


    @Query("select b from Book b join fetch b.bookKeywords bk join fetch bk.keywordItem where b.isbn = :isbn")
    Book findBookDetailWithKeywordByIsbn(String isbn);

    /**
     * 제목에 대한 검색 OR 키워드에 대한 검색 (리스트)
     * @param keywordNameList
     * @return
     */
    @Query("select b from Book b join fetch b.bookKeywords bk join fetch bk.keywordItem ki " +
            "where (LOWER(b.title) like %:searchWord% or LOWER(b.author) like %:searchWord%) and ki.name in :names")
    Page<Book> findPagingBySearchWordAndKeyword(@Param("searchWord") String searchWord,
                                                          @Param("names") List<String> keywordNameList, Pageable pageable);
}
