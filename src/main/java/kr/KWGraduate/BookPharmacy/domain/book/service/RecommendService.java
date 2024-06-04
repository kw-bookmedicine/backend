package kr.KWGraduate.BookPharmacy.domain.book.service;

import kr.KWGraduate.BookPharmacy.domain.book.domain.ClientRecommend;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BoardBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BookBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.ClientBasedRecommendDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BoardRecommendRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRecommendRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.ClientRecommendRepository;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final BoardRecommendRepository boardRecommendRepository;
    private final BookRecommendRepository bookRecommendRepository;
    private final ClientRecommendRepository clientRecommendRepository;
    private final ClientRepository clientRepository;

    public List<ClientBasedRecommendDto> getClientBasedAiPrescription(AuthenticationAdapter authentication){
        String username = authentication.getUsername();
        Client client = clientRepository.findByLoginId(username).get();

        List<ClientRecommend> clientRecommend = clientRecommendRepository.findByClientAiPrescription(client.getId());
        Collections.shuffle(clientRecommend);
        return clientRecommend.stream()
                .limit(3)
                .sorted(Comparator.comparingInt(ClientRecommend::getRank))
                .map(ClientBasedRecommendDto::new)
                .collect(Collectors.toList());
    }
    public List<ClientBasedRecommendDto> getClientBasedRecommend(AuthenticationAdapter authentication){
        String username = authentication.getUsername();
        Client client = clientRepository.findByLoginId(username).get();

        List<ClientRecommend> clientRecommend = clientRecommendRepository.findByClientBasedRecommend(client.getId());
        Collections.shuffle(clientRecommend);

        return clientRecommend.stream()
                .limit(10)
                .sorted(Comparator.comparingInt(ClientRecommend::getRank))
                .map(ClientBasedRecommendDto::new)
                .collect(Collectors.toList());
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
}
