package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.FeedDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import kr.KWGraduate.BookPharmacy.exception.status.ResourceNotFoundException;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;
    private final FeedRepository feedRepository;

    /**
     * isbn을 전달 받으면, 그 isbn을 통해 feed들을 페이징하여 조회 (특정 책의 feed들을 조회할 때 사용)
     * */
    public Page<FeedDto> getFeedsPagingByIsbn(String isbn, Pageable pageable){

        //페이징조회
//        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "registerDateTime"));
        Page<Feed> feedPageList = feedRepository.findPagingByBookIsbn(isbn, pageable);


        Page<FeedDto> feedDtoPage = feedPageList.map(feed -> new FeedDto()
                .setBookAttr(feed.getBook())
                .setFeedAttr(feed)
                .setClientAttr(feed.getClient()));

        return feedDtoPage;
    }

    /**
     * userId를 전달 받으면, 그 userId를 통해 평가를 남긴 feed들을 페이징하여 조회
     * */
    public Page<FeedDto> getRatedFeedsPagingByUserId(String userId, Pageable pageable) {

        Page<Feed> feedPageList = feedRepository.findRatedFeedsPagingByLoginId(userId, pageable);

        Page<FeedDto> feedDtoPage = feedPageList.map(feed -> new FeedDto()
                .setBookAttr(feed.getBook())
                .setFeedAttr(feed)
                .setClientAttr(feed.getClient()));

        return feedDtoPage;
    }

    /**
    * 피드 모아보기 페이지에서 사용하는 전체 피드 조회 서비스
    * */
    public Page<FeedDto> getFeedsPagingAndSortingRegisterTime(Pageable pageable) {
        Page<Feed> feedPage = feedRepository.findPagingAndSorting(pageable);

        Page<FeedDto> feedDtoPage = feedPage.map(feed -> new FeedDto()
                .setBookAttr(feed.getBook())
                .setFeedAttr(feed)
                .setClientAttr(feed.getClient()));

        return feedDtoPage;
    }

    /**
     * FeedDto와 clientId를 전달받으면, FeedDto.isbn과 userId 통하여 피드를 수정
     */
    public FeedDto updateFeed(FeedDto feedDto, String userId) {

        String isbn = feedDto.getBookIsbn();
        Feed feed = feedRepository.findOptionalByLoginIdAndIsbn(userId, isbn)
                .orElseThrow(() -> new ResourceNotFoundException("feed doesn't exist. -> userId: " + userId + " isbn: " + isbn));

        feed.setRated(true);
        feed.setRating(feedDto.getRating());
        feed.setComment(feedDto.getComment());

        Feed modifiedFeed = feedRepository.saveAndFlush(feed);

        feedDto.setFeedAttr(modifiedFeed);

        return feedDto;
    }

    /**
     * FeedDto와 clientId를 전달받으면, FeedDto.isbn과 userId 통하여 피드를 삭제
     */
    public void deleteFeed(String isbn, String userId) {

        Feed feed = feedRepository.findOptionalByLoginIdAndIsbn(userId, isbn)
                .orElseThrow(() -> new ResourceNotFoundException("feed doesn't exist. -> userId: " + userId + " isbn: " + isbn));

        feedRepository.delete(feed);
    }

    /**
     * 책을 읽은 경험 추가하기
     */

}
