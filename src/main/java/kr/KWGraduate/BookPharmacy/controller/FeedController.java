package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.FeedDto;
import kr.KWGraduate.BookPharmacy.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
@Tag(name = "Feed")
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "모든 feed 요청", description = "모든 feed들을 페이징해서 요청 예) /api/feed/all?page=0&size=5")
    @GetMapping("/all")
    public ResponseEntity<Page<FeedDto>> getAllFeedPaging(@Parameter(name = "page", description = "page는 기본 0부터 시작")
                                               @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<FeedDto> result = feedService.getFeedsPagingAndSortingRegisterTime(pageable);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "책에 해당하는 feed 요청", description = "해당 책에 대한 feed들을 페이징해서 " +
            "요청 예) /api/feed/book?title=java&author=이성훈&publishYear=2022&page=0&size=5")
    @GetMapping("/book")
    public ResponseEntity<Page<FeedDto>> getByBookAttr(@RequestParam(name ="title") String title,
                                                        @RequestParam(name ="author") String author,
                                                        @RequestParam(name ="publishYear") String publishYear,
                                                        @Parameter(name = "page", description = "page는 기본 0부터 시작")
                                            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable ){
        BookDto bookDto = BookDto.builder().title(title).author(author).publicYear(publishYear).build();
        Page<FeedDto> result = feedService.getFeedsPagingByBookDto(bookDto, pageable);

        return ResponseEntity.ok(result);
    }

}