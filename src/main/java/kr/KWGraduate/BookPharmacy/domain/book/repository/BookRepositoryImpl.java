package kr.KWGraduate.BookPharmacy.domain.book.repository;

import static kr.KWGraduate.BookPharmacy.domain.book.domain.QBook.book;
import static kr.KWGraduate.BookPharmacy.domain.category.domain.QCategories.categories;
import static kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.QBookKeyword.bookKeyword;
import static kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.QKeywordItem.keywordItem;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.QBookKeyword;
import kr.KWGraduate.BookPharmacy.domain.keyworditem.domain.QKeywordItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.support.PageableExecutionUtils;

public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookSearchResponseDto> findSearchBookDtoByCategory(Long categoryId, Pageable pageable) {

        List<BookSearchResponseDto> content = queryFactory
            .select(Projections.fields(BookSearchResponseDto.class,
                book.id.as("bookId"),
                book.title,
                book.author,
                categories.name.as("middleCategoryName"),
                book.publishingHouse,
                book.publishYear,
                book.imageUrl))
            .from(book)
            .join(book.middleCategory, categories)
            .where(categories.id.eq(categoryId))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(book.count())
            .from(book)
            .join(book.middleCategory, categories)
            .where(categories.id.eq(categoryId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);

    }


    @Override
    public List<BookSearchResponseDto> findDtoBook10ListByMiddleCategory(Long categoryId) {

        List<BookSearchResponseDto> content = queryFactory.select(
                Projections.fields(BookSearchResponseDto.class,
                    book.id.as("bookId"),
                    book.title,
                    book.author,
                    categories.name.as("middleCategoryName"),
                    book.publishingHouse,
                    book.publishYear,
                    book.imageUrl))
            .from(book)
            .join(book.middleCategory, categories)
            .where(categories.id.eq(categoryId))
            .limit(10)
            .fetch();

        return content;
    }

}
