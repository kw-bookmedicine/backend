package kr.KWGraduate.BookPharmacy.domain.book.service;

import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import kr.KWGraduate.BookPharmacy.domain.category.dto.response.CategoryDto;
import kr.KWGraduate.BookPharmacy.domain.category.repository.CategoryRepository;
import kr.KWGraduate.BookPharmacy.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    /**
     * - 현재 리팩토링이 되지 않은 코드임 (중분류마다 쿼리가 발생하여, 대분류 1번 + 중분류 10번 = 총 11번의 쿼리가 발생하는 매우 비효율적인 코드)
     * 다음과 같은 몇 가지 방법으로 리팩토링을 진행할 수 있음
     * 1. group by 를 통해 1번의 쿼리로 줄일 수 있음
     *    -> 책 table의 크기가 크기 때문에 비효율적으로 동작할 것으로 예상
     * 2. 중분류별로 10권씩 대표적인 책을 저장하는 테이블을 따로 관리하여, 간단하게 쿼리를 작성할 수 있음
     *    -> 1번의 쿼리 + 빠른 응답속도 라는 장점이 있지만, 테이블을 따로 관리해주어야 함
     *    -> 현재 우리가 쓰고 있는 관심사-책 테이블인 InterestRecommend 테이블을 사용할 수도 있음
     *
     * 위와 같은 이유로, 리팩토링에 대해서는 팀원과 이야기를 나눈 후 리팩토링을 진행해야 할 것으로 보임
     * */
    // 대분류 페이지에서 대분류에 속하는 중분류 이름 별 조회에 사용
    public List<Map<String, Object>> getBookListByBigCategoryChildren(Long bigCategoryId){

        List<Map<String, Object>> result = new ArrayList<>();

        // 대분류에 속하는 중분류들을 조회하고, 그 중분류들에 해당하는 책들 10권을 Map에 추가함
        List<Categories> childCategories = categoryRepository.findChildrenByBigCategoryId(
            bigCategoryId);

        for (Categories childCategory : childCategories) {
            Map<String, Object> middleCategoryInfo = new HashMap<>();

            String categoryName = childCategory.getName();
            Long categoryId = childCategory.getId();
            middleCategoryInfo.put("categoryName", categoryName);

            List<BookSearchResponseDto> bookDtoList = bookRepository.findDtoBook10ListByMiddleCategory(categoryId);

            middleCategoryInfo.put("bookList", bookDtoList);

            result.add(middleCategoryInfo);
        }

        return result;
    }

    // 중분류 페이지에서 중분류 이름 별 조회에 사용
    public Page<BookSearchResponseDto> getBookPageByMiddleCategory(Long categoryId, Pageable pageable){

        int pageNumber = 0;
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<BookSearchResponseDto> bookPageList = bookRepository.findSearchBookDtoByCategory(categoryId, pageRequest);

        return bookPageList;
    }

    @Transactional
    public BookDto getBookDetails(Long id){
        Optional<Book> optional = bookRepository.findBookWithKeywordByBookId(id);

        // 예외처리 추가해야함
        Book book = optional.get();

        book.plusViewCount();

        BookDto bookDto = BookDto.toDtoWithKeywordDto(book);

        return bookDto;
    }
}
