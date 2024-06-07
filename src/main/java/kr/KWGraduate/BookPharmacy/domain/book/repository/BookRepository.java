package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
     * 검색어로 제목에 대하여 한줄처방 개수 or 조회수로 정렬하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b where b.title like %:searchWord%")
    Page<Book> findPagingByTitleOrderByCount(@Param("searchWord") String searchWord, Pageable pageable);

    /**
     * 검색어로 작가명에 대하여 한줄처방 개수 or 조회수로 정렬하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b where b.author like %:searchWord%")
    Page<Book> findPagingByAuthorOrderByCount(@Param("searchWord") String searchWord, Pageable pageable);


    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b left join b.bookKeywords bk left join bk.keywordItem where b.isbn = :isbn")
    Book findBookWithKeywordByIsbn(@Param("isbn") String isbn);

    /**
     * 해당 키워드를 갖고 있는 책을 한줄처방 개수로 정렬하여 검색 (리스트)
     * @param keywordName
     * @return
     */
    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b left join b.bookKeywords bk left join bk.keywordItem ki " +
            "left join OneLinePrescription op on op.book.id = b.id " +
            "where ki.name = :keywordName")
    Page<Book> findPagingByKeyword(@Param("keywordName") String keywordName, Pageable pageable);

    /**
     * 제목 및 작가에 대한 검색 OR 키워드에 대한 검색 (리스트)
     * @param keywordNameList
     * @return
     */
    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b left join b.bookKeywords bk left join bk.keywordItem ki " +
            "where (LOWER(b.title) like %:searchWord% or LOWER(b.author) like %:searchWord%) and ki.name in :names")
    Page<Book> findPagingBySearchWordAndKeyword(@Param("searchWord") String searchWord,
                                                          @Param("names") List<String> keywordNameList, Pageable pageable);

    @Query("select b from Book b inner join ReadExperience re on b.id = re.book.id " +
            "join fetch b.middleCategory m " +
            "where m.id = :categoryId group by b.id " +
            "order by count(re.id) desc")
    Slice<Book> findPopularByCategory(@Param("categoryId") Long categoryId, Pageable pageable);
}
