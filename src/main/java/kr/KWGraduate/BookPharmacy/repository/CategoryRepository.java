package kr.KWGraduate.BookPharmacy.repository;

import kr.KWGraduate.BookPharmacy.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Categories, Long> {

    /**
     * 부모 카테고리 이름으로 자식 카테고리 조회하기
     * */
    @Query("select c from Categories c join fetch c.parentCategory pc where pc.name = :categoryName")
    List<Categories> findChildrenByBigCategoryName(@Param("categoryName")String bigCategoryName);

    /**
     * 대분류(level=1)들을 조회하기
     * */
    @Query("select c from Categories c where c.level = 1")
    List<Categories> findBigCategory();
}
