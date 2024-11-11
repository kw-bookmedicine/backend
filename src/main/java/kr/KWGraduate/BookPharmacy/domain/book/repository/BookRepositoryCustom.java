package kr.KWGraduate.BookPharmacy.domain.book.repository;

import java.util.List;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface BookRepositoryCustom {
    Page<BookSearchResponseDto> findSearchBookDtoByCategory(Long categoryId, Pageable pageable);

    List<BookSearchResponseDto>findDtoBook10ListByMiddleCategory(Long categoryId);
}
