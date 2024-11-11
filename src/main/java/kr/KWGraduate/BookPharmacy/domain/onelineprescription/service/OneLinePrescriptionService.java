package kr.KWGraduate.BookPharmacy.domain.onelineprescription.service;

import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineCreateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.request.OneLineUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.dto.response.OneLineResponseDto;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLineHelpfulEmotionRepository;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.repository.OneLineLikeEmotionRepository;
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
    private final OneLineLikeEmotionRepository oneLineLikeEmotionRepository;
    private final OneLineHelpfulEmotionRepository oneLineHelpfulEmotionRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public OneLinePrescription createOneLinePrescription(OneLineCreateDto oneLineCreateDto, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        Long bookId = oneLineCreateDto.getBookId();
        Book book = bookRepository.findOptionalById(bookId).get();

        OneLinePrescription oneLinePrescription = oneLineCreateDto.toEntity(book);

        oneLinePrescription.setClientAndBook(client, book);
        OneLinePrescription savedResult = oneLinePrescriptionRepository.save(oneLinePrescription);

        client.plusOneLineCount();
        book.plusOneLineCount();

        return savedResult;
    }

    @Transactional
    public void updateOneLinePrescription(Long oneLinePrescriptionId, OneLineUpdateDto oneLineUpdateDto, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();

        Long bookId = oneLineUpdateDto.getBookId();
        Book afterBook = bookRepository.findOptionalById(bookId).get();

        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findById(oneLinePrescriptionId).get();
        Book beforeBook = oneLinePrescription.getBook();

        beforeBook.minusOneLineCount();
        afterBook.plusOneLineCount();

        oneLinePrescription.setTitle(oneLineUpdateDto.getTitle());
        oneLinePrescription.setDescription(oneLineUpdateDto.getDescription());
        oneLinePrescription.setKeyword(oneLineUpdateDto.getKeyword());
        oneLinePrescription.setBook(afterBook);
    }

    @Transactional
    public void deleteOneLinePrescription(Long oneLinePrescriptionId, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        OneLinePrescription prescription = oneLinePrescriptionRepository.findById(oneLinePrescriptionId).get();
        Book book = prescription.getBook();

        oneLinePrescriptionRepository.delete(prescription);
        client.minusOneLineCount();
        book.minusOneLineCount();

    }

    public OneLineResponseDto getOneLinePrescription(Long oneLinePrescriptionId, AuthenticationAdapter authentication) {
        String loginId = authentication.getUsername();

        OneLinePrescription oneLinePrescription = oneLinePrescriptionRepository.findOneLinePrescriptionById(oneLinePrescriptionId).get();
        OneLineResponseDto dto = new OneLineResponseDto()
                .setAllAttr(oneLinePrescription.getBook(), oneLinePrescription.getClient(), oneLinePrescription)
                .setIsLike(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLinePrescription.getId()).isPresent())
                .setIsHelpful(oneLineHelpfulEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLinePrescription.getId()).isPresent());

        return dto;
    }

    public Page<OneLineResponseDto> getAllOneLinePrescriptions(AuthenticationAdapter authentication, Pageable pageable) {
        String loginId = authentication.getUsername();

        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findPagingAll(pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine)
                .setIsLike(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent())
                .setIsHelpful(oneLineHelpfulEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent()));

        return dtoList;
    }

    public Page<OneLineResponseDto> getOneLinePrescriptionsByKeyword(Keyword keyword, AuthenticationAdapter authentication, Pageable pageable) {
        String loginId = authentication.getUsername();

        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByKeyword(keyword, pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine)
                .setIsLike(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent())
                .setIsHelpful(oneLineHelpfulEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent()));

        return dtoList;
    }

    public Page<OneLineResponseDto> getOneLinePrescriptionsBySearch(String searchWord, AuthenticationAdapter authentication, Pageable pageable) {
        String loginId = authentication.getUsername();

        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByTitleContainingOrDescriptionContaining(searchWord, pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine)
                .setIsLike(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent())
                .setIsHelpful(oneLineHelpfulEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent()));

        return dtoList;
    }

    public Page<OneLineResponseDto> getOneLinePrescriptionsByBook(Long bookId, AuthenticationAdapter authentication, Pageable pageable) {
        String loginId = authentication.getUsername();

        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByBookId(bookId, pageable);
        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine)
                .setIsLike(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent())
                .setIsHelpful(oneLineHelpfulEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent()));

        return dtoList;

    }

    public Page<OneLineResponseDto> getMyOneLinePrescriptions(AuthenticationAdapter authentication, Pageable pageable) {
        String loginId = authentication.getUsername();
        Page<OneLinePrescription> pageResult = oneLinePrescriptionRepository.findByLoginId(loginId, pageable);

        Page<OneLineResponseDto> dtoList = pageResult.map(oneLine -> new OneLineResponseDto()
                .setAllAttr(oneLine.getBook(), oneLine.getClient(), oneLine)
                .setIsLike(oneLineLikeEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent())
                .setIsHelpful(oneLineHelpfulEmotionRepository.findByLoginIdAndOneLinePreId(loginId, oneLine.getId()).isPresent()));

        return dtoList;
    }
}
