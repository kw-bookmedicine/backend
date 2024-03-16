package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.FeedDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import kr.KWGraduate.BookPharmacy.exception.status.NoExistIdException;
import kr.KWGraduate.BookPharmacy.exception.status.ResourceNotFoundException;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
     * FeedDto를 전달받으면, FeedDto.feedId를 통하여 피드를 수정
     */
    public FeedDto updateFeed(FeedDto feedDto) {

        Long feedId = feedDto.getFeedId();
        Optional<Feed> optional = feedRepository.findById(feedId);
        Feed feed;

        if(optional.isEmpty()){
            feed = new Feed();
            String isbn = feedDto.getBookIsbn();
            String clientNickname = feedDto.getClientNickname();

            Book book = bookRepository.findOptionalByIsbn(isbn)
                    .orElseThrow(() -> new ResourceNotFoundException("book doesn't exist. -> isbn: " + isbn));
            Client client = clientRepository.findByNickname(clientNickname)
                    .orElseThrow(() -> new NoExistIdException("nickname doesn't exist. -> " + clientNickname));
            // 연관관계 매핑
            feed.setClientAndBook(client, book);
        }else{
            feed = optional.get();
        }

        float afterRating = feedDto.getRating();
        String afterComment = feedDto.getComment();

        modifyBookInfoByFeedChange(feed, false, afterRating);

        feed.setRated(true);
        feed.setRating(afterRating);
        feed.setComment(afterComment);

        Feed modifiedFeed = feedRepository.saveAndFlush(feed);

        feedDto.setFeedAttr(modifiedFeed);

        return feedDto;
    }

    /**
     * FeedDto와 clientId를 전달받으면, FeedDto.isbn과 userId 통하여 피드를 삭제(독서경험이 사라지면 안됨)
     */
    public void deleteFeed(Long feedId) {

        Feed feed = feedRepository.findById(feedId)
                .orElseThrow(() -> new ResourceNotFoundException("feed doesn't exist."));

        float afterRating = 0;
        String afterComment = null;

        modifyBookInfoByFeedChange(feed, true, afterRating);

        // 독서 경험은 그대로 두면서 피드만 삭제
        feed.setRated(false);
        feed.setRating(afterRating); // 0
        feed.setComment(afterComment); // null

        feedRepository.saveAndFlush(feed);
    }

    /**
     * feed를 삭제함에 따라 책의 평균평점과 리뷰 수를 수정함
     */
    private void modifyBookInfoByFeedChange(Feed feed, boolean isRemoval, float afterFeedRating) {

        // 책의 평균 평점과 리뷰 개수를 수정하기 위함
        Book book = feed.getBook();

        int beforeReviewNum = book.getReviewNum(); // 리뷰 삭제 전 책의 리뷰 개수
        int afterReviewNum;                        // 리뷰 삭제 후 책의 리뷰 개수
        float beforeAverageRating = book.getRating();  // 리뷰 삭제 전 책의 평균 평점
        float beforeFeedRating = feed.getRating(); // 피드의 평점
        float beforeTotalReviewScore = beforeReviewNum * beforeAverageRating; // 리뷰 삭제 전 책의 리뷰 점수 총합 (책의 평균 평점 * 리뷰 개수)
        float afterTotalReviewScore; // 리뷰 삭제 후 책의 리뷰 점수 총합 (리뷰 삭제 전 책의 리뷰 점수 총합 - 피드의 평점)
        float afterAverageRating; // 리뷰 삭제 후 책의 평균 평점 (리뷰 삭제 후 책의 리뷰 점수 총합 / 리뷰 삭제 후 책의 리뷰 개수)

        if(isRemoval == true) {
            afterReviewNum = beforeReviewNum - 1;
            afterFeedRating = 0;
        }else{
            afterReviewNum = beforeReviewNum;
        }

        afterTotalReviewScore = beforeTotalReviewScore - beforeFeedRating + afterFeedRating; // 삭제의 경우 afterFeedRating=0
        afterAverageRating = afterTotalReviewScore / afterReviewNum;

        book.setRating(afterAverageRating);
        book.setReviewNum(afterReviewNum);
        bookRepository.saveAndFlush(book);
    }

    /**
     * 책을 읽은 경험 추가하기 (단일)
     */
    public Feed createSingleExperience(String isbn, String userId){
        Feed feed = new Feed();
        Book book = bookRepository.findOptionalByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException(
                "book doesn't exist. -> " + isbn));
        Client client = clientRepository.findByLoginId(userId).orElseThrow(() -> new NoExistIdException(
                "id doesn't exist. -> " + userId));

        feed.setClientAndBook(client, book);

        return feed;
    }

    /**
     * 책을 읽은 경험 추가하기 (리스트)
     */
    public List<Feed> createMultipleExperience(List<String> isbnList, String userId){
        Client client = clientRepository.findByLoginId(userId).orElseThrow(() -> new NoExistIdException(
                "id doesn't exist. -> " + userId));

        List<Feed> feedList = new ArrayList<>();

        List<Book> allByIsbn = bookRepository.findAllByIsbnIn(isbnList);

        for (Book book : allByIsbn) {
            Feed feed = new Feed();
            feed.setClientAndBook(client, book);

            feedList.add(feed);

            feedRepository.saveAllAndFlush(feedList);
        }

        return feedList;
    }

}
