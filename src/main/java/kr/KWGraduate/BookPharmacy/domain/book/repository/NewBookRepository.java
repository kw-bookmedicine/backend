package kr.KWGraduate.BookPharmacy.domain.book.repository;

import kr.KWGraduate.BookPharmacy.domain.book.domain.NewBook;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewBookRepository extends JpaRepository<NewBook, Long> {

    @EntityGraph(attributePaths = {"book"})
    @Query("SELECT b FROM NewBook b")
    List<NewBook> findAllNewBooks();
}
