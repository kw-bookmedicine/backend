package kr.KWGraduate.BookPharmacy.domain.category.repository;

import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
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

    @Query("select c from Categories c join fetch c.parentCategory pc where c.level = 2")
    List<Categories> findChildCategories();

    @Query("select c from Categories c where c.name in :categoryList")
    List<Categories> findCategoriesByList(@Param("categoryList") List<String> categoryList);

//    /**
//     * 대분류(level=1)들을 자식 카테고리와 함께 조회하기
//     * */
//    @Query("select pc.name, c.name FROM Categories c join fetch c.parentCategory pc GROUP BY pc.id")
//    List<Object[]> findMiddleGroupByBigCategory();
}
