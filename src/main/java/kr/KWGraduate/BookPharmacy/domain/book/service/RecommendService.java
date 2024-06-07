package kr.KWGraduate.BookPharmacy.domain.book.service;

import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.domain.InterestRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BoardBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.ClientBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.*;
import kr.KWGraduate.BookPharmacy.domain.category.domain.Categories;
import kr.KWGraduate.BookPharmacy.domain.category.repository.CategoryRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.interest.domain.Interest;
import kr.KWGraduate.BookPharmacy.domain.interest.repository.InterestRepository;
import kr.KWGraduate.BookPharmacy.domain.readexperience.domain.ReadExperience;
import kr.KWGraduate.BookPharmacy.domain.readexperience.repository.ReadExperienceRepository;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final BoardRecommendRepository boardRecommendRepository;
    private final BookRecommendRepository bookRecommendRepository;
    private final ClientRecommendRepository clientRecommendRepository;
    private final InterestRepository interestRepository;
    private final InterestRecommendRepository interestRecommendRepository;
    private final ClientRepository clientRepository;
    private final ReadExperienceRepository readExperienceRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    public List<ClientBasedRecommendDto> getClientBasedAiPrescription(AuthenticationAdapter authentication){
        String username = authentication.getUsername();
        Client client = clientRepository.findByLoginId(username).get();

        List<ClientBasedRecommendDto> dtoList;

        List<ReadExperience> readExperienceList = readExperienceRepository.findByLoginId(username);

        PageRequest pageRequest = PageRequest.of(0, 3);

        if(readExperienceList.isEmpty()) { // 독서경험이 0개일 경우 처리

            List<InterestRecommend> interestRecommend = getInterestRecommend(username, pageRequest);

            dtoList = interestRecommend.stream().map(interest -> new ClientBasedRecommendDto(interest)).collect(Collectors.toList());

        }else {        // 독서경험이 1개 이상 존재할 경우

            List<ClientRecommend> clientRecommend = clientRecommendRepository.findByClientBasedRecommend(client.getId());

            if(clientRecommend.isEmpty()) {  // ClientRecommend가 존재하지 않는다면 관심사 기반 추천리스트를 반환
                List<InterestRecommend> interestRecommend = getInterestRecommend(username, pageRequest);

                dtoList = interestRecommend.stream().map(interest -> new ClientBasedRecommendDto(interest)).collect(Collectors.toList());
            }else {  // ClientRecommend가 존재한다면 유저 기반 추천리스트를 반환
                Collections.shuffle(clientRecommend);

                dtoList = clientRecommend.stream().limit(3).sorted(Comparator.comparingInt(ClientRecommend::getRank))
                        .map(ClientBasedRecommendDto::new).collect(Collectors.toList());
            }
        }

        return dtoList;
    }
    public List<ClientBasedRecommendDto> getClientBasedRecommend(AuthenticationAdapter authentication){
        String username = authentication.getUsername();
        Client client = clientRepository.findByLoginId(username).get();

        List<ClientBasedRecommendDto> dtoList;

        List<ReadExperience> readExperienceList = readExperienceRepository.findByLoginId(username);

        PageRequest pageRequest = PageRequest.of(0, 10);

        if(readExperienceList.isEmpty()) { // 독서경험이 0개일 경우 처리

            List<InterestRecommend> interestRecommend = getInterestRecommend(username, pageRequest);

            dtoList = interestRecommend.stream().map(interest -> new ClientBasedRecommendDto(interest)).collect(Collectors.toList());

        } else {        // 독서경험이 1개 이상 존재할 경우

            List<ClientRecommend> clientRecommend = clientRecommendRepository.findByClientBasedRecommend(client.getId());

            if(clientRecommend.isEmpty()) {  // ClientRecommend가 존재하지 않는다면 관심사 기반 추천리스트를 반환
                List<InterestRecommend> interestRecommend = getInterestRecommend(username, pageRequest);

                dtoList = interestRecommend.stream().map(interest -> new ClientBasedRecommendDto(interest)).collect(Collectors.toList());
            }else {  // ClientRecommend가 존재한다면 유저 기반 추천리스트를 반환
                Collections.shuffle(clientRecommend);

                dtoList = clientRecommend.stream().limit(10).sorted(Comparator.comparingInt(ClientRecommend::getRank))
                        .map(ClientBasedRecommendDto::new).collect(Collectors.toList());
            }
        }

        return dtoList;
    }
    public BoardBasedRecommendDto getBoardBasedRecommend(Long boardId){
        return boardRecommendRepository.findByBoardBasedRecommend(boardId)
                .map((boardRecommend -> {
                    if(boardRecommend.getBook() == null){
                        return new BoardBasedRecommendDto(false);
                    }
                    else{
                        return new BoardBasedRecommendDto(boardRecommend);
                    }
                })
                )
                .orElseGet(() -> new BoardBasedRecommendDto(true));
    }
    public List<BookBasedRecommendDto> getBookBasedRecommend(String isbn){
        return bookRecommendRepository.findByBookBasedRecommend(isbn).stream()
                .map(BookBasedRecommendDto::new)
                .collect(Collectors.toList());
    }

    // 스케쥴러에 의해 관심사 추천 도서를 등록하는 함수
    public void setInterestRecommend() {

        interestRecommendRepository.deleteAll();
        interestRecommendRepository.flush();

        List<Categories> childCategories = categoryRepository.findChildCategories();

        PageRequest pageRequest = PageRequest.of(0, 30);

        for (Categories category: childCategories) {
            Long categoryId = category.getId();
            List<Book> popularBookList = bookRepository.findPopularByCategory(categoryId, pageRequest).getContent();
            List<InterestRecommend> interestRecommendList = popularBookList.stream()
                    .map(book -> new InterestRecommend(book)).collect(Collectors.toList());
            interestRecommendRepository.saveAll(interestRecommendList);
        }
    }

    private List<InterestRecommend> getInterestRecommend(String username, Pageable pageable) {
        List<Interest> interestList = interestRepository.findByLoginId(username);  // 유저의 관심사리스트

        List<InterestRecommend> interestRecommend;

        if(interestList.isEmpty()) // 유저가 관심사를 하나도 등록하지 않았을 경우
        {
            interestRecommend = interestRecommendRepository.findRandAll(pageable);

        }else{
            List<Long> interestIdList = interestList.stream().map(interest -> interest.getId()).collect(Collectors.toList());
            interestRecommend = interestRecommendRepository.findByInterestList(interestIdList, pageable);
        }

        return interestRecommend;
    }
}
