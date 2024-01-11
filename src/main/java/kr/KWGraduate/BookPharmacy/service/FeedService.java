package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.FeedDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final ClientRepository clientRepository;
    private final BookRepository bookRepository;
    private final FeedRepository feedRepository;

    /**
     * BookDto를 전달 받으면, 그 BookDto의 attribute들을 통해 feeds를 페이징하여 조회 (특정 책의 feed들을 조회할 때 사용)
     * */
    public Page<FeedDto> getFeedsPagingByBookDto(BookDto bookDto, Pageable pageable){

        // distinct한 책 조회 (우선은 title, author, publishYear로 unique한 값을 찾을 수 있다고 가정하겠음)
        String title = bookDto.getTitle();
        String author = bookDto.getAuthor();
        String publishYear = bookDto.getPublicYear();
        Book book = bookRepository.findByTitleAndAuthorAndPublishYear(title, author, publishYear).get();

        //페이징조회
//        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "registerDateTime"));
        Page<Feed> feedPageList = feedRepository.findPagingByBookId(book.getId(), pageable);


        Page<FeedDto> feedDtoPage = feedPageList.map(feed -> new FeedDto()
                .setBookAttr(book)
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
}
