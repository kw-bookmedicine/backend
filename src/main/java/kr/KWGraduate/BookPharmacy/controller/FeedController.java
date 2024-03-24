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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feeds")
@RequiredArgsConstructor
@Tag(name = "Feed")
public class FeedController {

    private final FeedService feedService;


    @Operation(summary = "모든 feed 요청", description = "모든 feed들을 페이징해서 요청 예) /api/feeds/all?page=0&size=5")
    @GetMapping("/all")
    public ResponseEntity<Page<FeedDto>> getAllFeedPaging(@Parameter(name = "page", description = "page는 기본 0부터 시작")
                                               @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){
        Page<FeedDto> result = feedService.getFeedsPagingAndSortingRegisterTime(pageable);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "책에 해당하는 feed 요청", description = "해당 책에 대한 feed들을 페이징해서 " +
            "요청 예) /api/feeds/book?isbn=1234-5678-1&page=0&size=5")
    @GetMapping("/book")
    public ResponseEntity<Page<FeedDto>> getByBookAttr(@RequestParam(name ="isbn") String isbn,
                                                        @Parameter(name = "page", description = "page는 기본 0부터 시작")
                                            @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable){

        Page<FeedDto> result = feedService.getFeedsPagingByIsbn(isbn, pageable);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "해당 유저의 모든 feed를 조회(리뷰를 남긴 것만)", description = "유저의 feed들을 페이징하여 반환" +
            "요청 예) /api/feeds?userId=sim&page=0&size=5")
    @GetMapping("")
    public ResponseEntity<Page<FeedDto>> getAllFeedsByUser(@RequestParam(name = "userId") String userId,
                                                           @Parameter(name = "page", description = "page는 기본 0부터 시작")
                                           @PageableDefault(sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<FeedDto> result = feedService.getRatedFeedsPagingByUserId(userId, pageable);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "feed를 업데이트함 (등록, 수정)", description = "요청 예) /api/feeds/1")
    @PutMapping("/{feedId}")
    public ResponseEntity<FeedDto> updateFeed(FeedDto feedDto, @PathVariable("feedId") String feedId){

        FeedDto modifiedFeed = feedService.updateFeed(feedDto);

        return ResponseEntity.ok(modifiedFeed);
    }

    @Operation(summary = "feed를 삭제함 (삭제)", description = "요청 예)  /api/feeds/1")
    @DeleteMapping("/{feedId}")
    public ResponseEntity deleteFeed(@PathVariable("feedId") Long feedId){

        feedService.deleteFeed(feedId);

        return (ResponseEntity) ResponseEntity.ok();
    }

    @Operation(summary = "읽은 경험 한번에 추가하기")
    @PostMapping("/experiences")
    public ResponseEntity addReadingExperiences(List<String> isbnList, @RequestParam(name = "userId") String userId){

        feedService.createMultipleExperience(isbnList, userId);

        return (ResponseEntity) ResponseEntity.ok();
    }
}