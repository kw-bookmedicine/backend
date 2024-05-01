package kr.KWGraduate.BookPharmacy.service;

import kr.KWGraduate.BookPharmacy.dto.client.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.request.OneLineCreateDto;
import kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.request.OneLineUpdateDto;
import kr.KWGraduate.BookPharmacy.entity.Book;
import kr.KWGraduate.BookPharmacy.entity.Client;
import kr.KWGraduate.BookPharmacy.entity.Keyword;
import kr.KWGraduate.BookPharmacy.entity.OneLinePrescription;
import kr.KWGraduate.BookPharmacy.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.repository.OneLinePrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OneLinePrescriptionService {

    private final OneLinePrescriptionRepository oneLinePrescriptionRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public OneLinePrescription createOneLinePrescription(OneLineCreateDto oneLineCreateDto, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        String isbn = oneLineCreateDto.getBookIsbn();
        Book book = bookRepository.findOptionalByIsbn(isbn).get();

        // 추후 Mapper 클래스에서 enum값을 가져오도록 수정해야함 (일단은 경제경영으로 설정)
        Keyword keyword = Keyword.Economy_Management;

        OneLinePrescription oneLinePrescription = OneLinePrescription.builder()
                .title(oneLineCreateDto.getTitle())
                .description(oneLineCreateDto.getDescription())
                .keyword(keyword)
                .build();

        oneLinePrescription.setClientAndBook(client, book);

        return oneLinePrescriptionRepository.save(oneLinePrescription);
    }

    @Transactional
    public void updateOneLinePrescription(Long oneLinePrescriptionId, OneLineUpdateDto oneLineUpdateDto, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        String bookIsbn = oneLineUpdateDto.getBookIsbn();
        Book book = bookRepository.findOptionalByIsbn(bookIsbn).get();

        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findById(oneLinePrescriptionId).get();

        oneLinePrescription.setTitle(oneLineUpdateDto.getTitle());
        oneLinePrescription.setDescription(oneLineUpdateDto.getDescription());
        oneLinePrescription.setBook(book);

        oneLinePrescriptionRepository.flush();
    }

    @Transactional
    public void deleteOneLinePrescription(Long oneLinePrescriptionId, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        OneLinePrescription prescription = oneLinePrescriptionRepository.findById(oneLinePrescriptionId).get();

        oneLinePrescriptionRepository.delete(prescription);

        oneLinePrescriptionRepository.flush();
    }

//    Keyword 매핑 서비스 병합 후 작성
//    public List<OneLinePrescription> getOneLinePrescriptionByKeyword(String keyword) {
//
//    }
}
