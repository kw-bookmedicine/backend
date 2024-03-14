package kr.KWGraduate.BookPharmacy.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.dto.FeedDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Categories;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Feed;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.CategoryRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.FeedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback
class FeedServiceTest {

    @Autowired FeedRepository feedRepository;
    @Autowired FeedService feedService;
    @Autowired ClientRepository clientRepository;
    @Autowired BookRepository bookRepository;
    @Autowired CategoryRepository categoryRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void pagingTest(){

        //when
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<FeedDto> result = feedService.getFeedsPagingAndSortingRegisterTime(pageRequest);

        for (FeedDto feedDto : result) {
            System.out.println("feedDto = " + feedDto);
        }
    }

    @Test
    @DisplayName("피드 수정하기")
    public void updateTest(){
        //given
        Feed feed = feedRepository.findById(1L).get();
        Client client = clientRepository.findByNickname(feed.getClient().getNickname()).get();
        String userId = client.getLoginId();

        Book book = feed.getBook();
        float beforeRating = book.getRating();

        FeedDto feedDto = new FeedDto().setFeedAttr(feed).setBookAttr(feed.getBook()).setClientAttr(feed.getClient());

        em.flush();

        //when
        feedDto.setComment("마음이 다시 바뀜");
        feedDto.setRating(3.0f);

        FeedDto updatedFeedDto = feedService.updateFeed(feedDto, userId); // updateFeed 함수 마지막에 flush가 동작함

        //then
        Feed findFeed = feedRepository.findById(1L).get();
        FeedDto findFeedDto = new FeedDto().setFeedAttr(findFeed).setBookAttr(feed.getBook()).setClientAttr(feed.getClient());

        Assertions.assertThat(updatedFeedDto).isEqualTo(findFeedDto);

        System.out.println("책의 평균평점 변화 = " + beforeRating + " -> " + findFeed.getBook().getRating());
    }

    @Test
    @DisplayName("피드 삭제하기")
    public void deleteTest(){
        //given
        Feed feed = feedRepository.findById(1L).get();
        Client client = clientRepository.findByNickname(feed.getClient().getNickname()).get();
        Book book = feed.getBook();
        String isbn = book.getIsbn();
        float beforeRating = book.getRating();
        String userId = client.getLoginId();

        em.flush();

        //when
        feedService.deleteFeed(isbn, userId);// updateFeed 함수 마지막에 flush가 동작함

        //then
        Feed findFeed = feedRepository.findById(1L).get();

        Assertions.assertThat(findFeed.getComment()).isEqualTo(null);
        Assertions.assertThat(findFeed.getRating()).isEqualTo(0);

        System.out.println("책의 평균평점 변화 = " + beforeRating + " -> " + findFeed.getBook().getRating());
    }

    @Test
    @DisplayName("읽은경험 여러개 한번에 추가하기")
    public void createMultipleExperience(){
        Client client = clientRepository.findById(10L).get();
        String userId = client.getLoginId();

        List<String> isbnList = new ArrayList<>();
        isbnList.add("9788901126050");
        isbnList.add("9788957076774");
        isbnList.add("9788959062195");

        feedService.createMultipleExperience(isbnList, userId);
    }
}