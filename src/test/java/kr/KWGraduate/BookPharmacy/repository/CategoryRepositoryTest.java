package kr.KWGraduate.BookPharmacy.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.entity.Categories;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class CategoryRepositoryTest {

    @Autowired CategoryRepository categoryRepository;
    @PersistenceContext EntityManager em;

    @Test
    @DisplayName("자식 카테고리들 조회하기")
    public void findChildren(){
        //given
        Categories category1 = Categories.builder().name("시").level(1).build();
        Categories category2 = Categories.builder().name("한국시").parentCategory(category1).level(2).build();
        Categories category3 = Categories.builder().name("중국시").parentCategory(category1).level(2).build();
        Categories category4 = Categories.builder().name("일본시").parentCategory(category1).level(2).build();
        Categories category5 = Categories.builder().name("소설").level(1).build();
        Categories category6 = Categories.builder().name("한국소설").parentCategory(category5).level(2).build();
        Categories category7 = Categories.builder().name("일본소설").parentCategory(category5).level(2).build();
        Categories category8 = Categories.builder().name("중국소설").parentCategory(category5).level(2).build();

        //when
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        categoryRepository.save(category5);
        categoryRepository.save(category6);
        categoryRepository.save(category7);
        categoryRepository.save(category8);

        em.flush();
        em.clear();

        //then
        List<Categories> findCategories = categoryRepository.findChildrenByBigCategoryName("소설");
        Assertions.assertEquals(findCategories.size(), 3);
        for (Categories findCategory : findCategories) {
            System.out.println("findCategory = " + findCategory);
        }
    }

    @Test
    @DisplayName("대분류들 조회하기")
    public void findBigCategories(){
        //given
        Categories category1 = Categories.builder().name("시").level(1).build();
        Categories category2 = Categories.builder().name("한국시").parentCategory(category1).level(2).build();
        Categories category3 = Categories.builder().name("중국시").parentCategory(category1).level(2).build();
        Categories category4 = Categories.builder().name("일본시").parentCategory(category1).level(2).build();
        Categories category5 = Categories.builder().name("소설").level(1).build();
        Categories category6 = Categories.builder().name("한국소설").parentCategory(category5).level(2).build();
        Categories category7 = Categories.builder().name("일본소설").parentCategory(category5).level(2).build();
        Categories category8 = Categories.builder().name("중국소설").parentCategory(category5).level(2).build();

        //when
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
        categoryRepository.save(category4);
        categoryRepository.save(category5);
        categoryRepository.save(category6);
        categoryRepository.save(category7);
        categoryRepository.save(category8);

        em.flush();
        em.clear();

        List<Categories> findCategories = categoryRepository.findBigCategory();
        for (Categories findCategory : findCategories) {
            System.out.println("findCategory = " + findCategory);
        }
    }

}