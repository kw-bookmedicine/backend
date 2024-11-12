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

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    Optional<Book> findByTitleAndAuthorAndPublishYear(String title, String author, String publishYear);

    Optional<Book> findOptionalById(Long id);

    /**
     * 검색어로 제목에 대하여 한줄처방 개수 or 조회수로 정렬하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @Query(nativeQuery = true,
        value = "SELECT * FROM book b WHERE MATCH(b.title) AGAINST(:searchWord IN BOOLEAN MODE)")
    Page<Book> findPagingByTitleOrderByCount(@Param("searchWord") String searchWord, Pageable pageable);

    /**
     * 검색어로 작가명에 대하여 한줄처방 개수 or 조회수로 정렬하여 책 조회 (페이징)
     * @param searchWord
     * @return Page<Book>
     */
    @Query(nativeQuery = true,
        value = "SELECT * FROM book b WHERE MATCH(b.author) AGAINST(:searchWord IN BOOLEAN MODE)")
    Page<Book> findPagingByAuthorOrderByCount(@Param("searchWord") String searchWord, Pageable pageable);


    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b left join b.bookKeywords bk left join bk.keywordItem ki where b.id = :id")
    Optional<Book> findBookWithKeywordByBookId(@Param("id") Long id);

    /**
     * 해당 키워드를 갖고 있는 책을 한줄처방 개수로 정렬하여 검색 (리스트)
     * @param keywordName
     * @return
     */
    @EntityGraph(attributePaths = {"middleCategory"})
    @Query("select b from Book b join b.bookKeywords bk join bk.keywordItem ki where ki.name = :keywordName")
    Page<Book> findPagingByKeyword(@Param("keywordName") String keywordName, Pageable pageable);

    /**
     * 제목 및 작가에 대한 검색 OR 키워드에 대한 검색 (리스트)
     * @param keywordNameList
     * @return
     */
    @Query(nativeQuery = true,
        value = "select distinct b.* from book b join book_keyword bk on bk.book_id = b.book_id "
            + "join keyword_item ki on ki.keyword_id = bk.keyword_id "
            + "WHERE MATCH(b.title) AGAINST(:searchWord IN BOOLEAN MODE) and ki.name in :names",
    countQuery = "select count(distinct b.book_id) from book b join book_keyword bk on bk.book_id = b.book_id "
        + "join keyword_item ki on ki.keyword_id = bk.keyword_id "
        + "WHERE MATCH(b.title) AGAINST(:searchWord IN BOOLEAN MODE) and ki.name in :names")
    Page<Book> findPagingBySearchWordAndKeyword(@Param("searchWord") String searchWord,
                                                          @Param("names") List<String> keywordNameList, Pageable pageable);

    @Query("select b from Book b where b.middleCategory.id = :categoryId group by b.id order by b.oneLineCount desc")
    Slice<Book> findPopularByCategory(@Param("categoryId") Long categoryId, Pageable pageable);
}
