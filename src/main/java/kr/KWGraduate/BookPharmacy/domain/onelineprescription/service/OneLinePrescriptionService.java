package kr.KWGraduate.BookPharmacy.domain.onelineprescription.service;

import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineCreateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.response.OneLineResponseDto;
import kr.KWGraduate.BookPharmacy.domain.readexperience.service.ReadExperienceService;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.keyword.domain.Keyword;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.domain.OneLinePrescription;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLinePrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OneLinePrescriptionService {

    private final OneLinePrescriptionRepository oneLinePrescriptionRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;
    private final ReadExperienceService readExperienceService;

    @Transactional
    public OneLinePrescription createOneLinePrescription(OneLineCreateDto oneLineCreateDto, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        String isbn = oneLineCreateDto.getBookIsbn();
        Book book = bookRepository.findOptionalByIsbn(isbn).get();

        OneLinePrescription oneLinePrescription = oneLineCreateDto.toEntity(book);

        readExperienceService.createReadExperience(client, book);

        oneLinePrescription.setClientAndBook(client, book);
        OneLinePrescription savedResult = oneLinePrescriptionRepository.save(oneLinePrescription);

        return savedResult;
    }

    @Transactional
    public void updateOneLinePrescription(Long oneLinePrescriptionId, OneLineUpdateDto oneLineUpdateDto, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();

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

    public OneLineResponseDto getOneLinePrescription(Long oneLinePrescriptionId) {
        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findOneLinePrescriptionById(oneLinePrescriptionId).get();
        OneLineResponseDto dto = new OneLineResponseDto();
        dto.setAllAttr(oneLinePrescription.getBook(), oneLinePrescription.getClient(), oneLinePrescription);

        return dto;
    }

    public Page<OneLineResponseDto> getAllOneLinePrescriptions(Pageable pageable) {
        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findPagingAll(pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine));

        return dtoList;
    }

    public Page<OneLineResponseDto> getOneLinePrescriptionsByKeyword(Keyword keyword, Pageable pageable) {
        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByKeyword(keyword, pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine));

        return dtoList;
    }

    public Page<OneLineResponseDto> getOneLinePrescriptionsBySearch(String searchWord, Pageable pageable) {
        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByTitleContainingOrDescriptionContaining(searchWord, pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine));

        return dtoList;
    }

    public Page<OneLineResponseDto> getMyOneLinePrescriptions(AuthenticationAdapter authentication, Pageable pageable) {
        String loginId = authentication.getUsername();
        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByLoginId(loginId, pageable);

        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine));

        return dtoList;
    }
}
