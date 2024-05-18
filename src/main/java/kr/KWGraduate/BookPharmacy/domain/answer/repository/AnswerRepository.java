package kr.KWGraduate.BookPharmacy.domain.answer.repository;

import kr.KWGraduate.BookPharmacy.domain.answer.domain.Answer;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("select a from Answer a join fetch a.board b where b.id = :boardId")
    List<Answer> findByBoardId(@Param("boardId") Long boardId);

    @Query("select a from Answer a join fetch a.board b where b.keyword = :keyword")
    List<Answer> findByKeyword(@Param("keyword") Keyword keyword);

}
