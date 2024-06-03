package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
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
     * 중분류로 책 조회 ( 페이징 )
     */
    @EntityGraph(attributePaths = {"bigCategory"})
    @Query(value = "select distinct b from Book b join fetch b.middleCategory mc inner join b.bookKeywords where mc.name = :categoryName")
    Page<Book> findBookPagingByMiddleCategory(@Param("categoryName") String categoryName, Pageable pageable);

    @Query(value = "select new kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto(b.title, b.author, b.publishingHouse, b.publishYear, b.isbn, b.imageUrl) " +
            "from Book b join b.middleCategory mc where mc.name = :categoryName")
    Page<BookSearchResponseDto> findDtoBook10ListByMiddleCategory(@Param("categoryName") String categoryName, Pageable pageable);

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

    @EntityGraph(attributePaths = {"bigCategory", "middleCategory"})
    @Query("select b from Book b left join b.bookKeywords bk left join bk.keywordItem where b.isbn = :isbn")
    Book findBookDetailWithKeywordByIsbn(@Param("isbn") String isbn);

    /**
     * 해당 키워드를 갖고 있는 책을 검색 (리스트)
     * @param keywordName
     * @return
     */
    @EntityGraph(attributePaths = {"bigCategory", "middleCategory"})
    @Query("select b from Book b join fetch b.bookKeywords bk join fetch bk.keywordItem ki " +
            "where ki.name = :keywordName")
    Page<Book> findPagingByKeyword(@Param("keywordName") String keywordName, Pageable pageable);

    /**
     * 제목 및 작가에 대한 검색 OR 키워드에 대한 검색 (리스트)
     * @param keywordNameList
     * @return
     */
    @EntityGraph(attributePaths = {"bigCategory", "middleCategory"})
    @Query("select b from Book b left join b.bookKeywords bk left join bk.keywordItem ki " +
            "where (LOWER(b.title) like %:searchWord% or LOWER(b.author) like %:searchWord%) and ki.name in :names")
    Page<Book> findPagingBySearchWordAndKeyword(@Param("searchWord") String searchWord,
                                                          @Param("names") List<String> keywordNameList, Pageable pageable);
}
