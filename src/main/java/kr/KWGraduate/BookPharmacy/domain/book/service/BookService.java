package kr.KWGraduate.BookPharmacy.domain.book.service;

import jakarta.annotation.PostConstruct;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookDto;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.category.dto.response.CategoryDto;
import kr.KWGraduate.BookPharmacy.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    // 대분류 페이지에서 대분류에 속하는 중분류 이름 별 조회에 사용
    public List<Map<String, Object>> getBookListByBigCategoryChildren(String bigCategoryName, Pageable pageable){

        List<Map<String, Object>> result = new ArrayList<>();

        // 대분류에 속하는 중분류들을 조회하고, 그 중분류들에 해당하는 책들 10권을 Map에 추가함
        List<CategoryDto> childCategories = categoryService.getChildCategory(bigCategoryName);
        for (CategoryDto childCategory : childCategories) {
            Map<String, Object> middleCategoryInfo = new HashMap<>();

            String categoryName = childCategory.getName();
            middleCategoryInfo.put("categoryName", categoryName);

            Page<BookSearchResponseDto> bookDtoPageList = bookRepository.findDtoBook10ListByMiddleCategory(categoryName, pageable);
            List<BookSearchResponseDto> bookDtoList = bookDtoPageList.getContent();

            middleCategoryInfo.put("bookList", bookDtoList);

            result.add(middleCategoryInfo);
        }

        return result;
    }

    // 중분류 페이지에서 중분류 이름 별 조회에 사용
    public Page<BookDto> getBookPageByMiddleCategory(String categoryName, Pageable pageable){

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<Book> bookPageList = bookRepository.findBookPagingByMiddleCategory(categoryName, pageRequest);

        Page<BookDto> dtoList = bookPageList.map(book -> BookDto.toDtoWithKeywordDto(book));

        return dtoList;
    }

    public BookDto getBookDetails(String isbn){
        Book book = bookRepository.findBookWithKeywordByIsbn(isbn);
        book.plusViewCount();

        BookDto bookDto = BookDto.toDtoWithKeywordDto(book);

        return bookDto;
    }
}
