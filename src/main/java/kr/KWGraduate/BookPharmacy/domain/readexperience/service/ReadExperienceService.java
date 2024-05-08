package kr.KWGraduate.BookPharmacy.domain.readexperience.service;

import kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request.ReadExperienceCreateDto;
import kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request.ReadExperienceUpdateRequestDto;
import kr.KWGraduate.BookPharmacy.domain.readexperience.dto.response.ReadExperienceResponseDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.client.domain.Client;
import kr.KWGraduate.BookPharmacy.domain.readexperience.domain.ReadExperience;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.client.repository.ClientRepository;
import kr.KWGraduate.BookPharmacy.domain.readexperience.repository.ReadExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadExperienceService {
    private final ReadExperienceRepository readExperienceRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    /**
     * '독서 경험 수정하기' 모달창에서 사용되는 서비스 코드
     * 독서경험을 한번에 업데이트 한다.
     * */
    @Transactional
    public void updateReadExperience(ReadExperienceUpdateRequestDto readExperienceDTO, AuthenticationAdapter authentication){

        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        // 1. 기존에 있던 독서경험 리스트를 삭제함
        List<ReadExperience> previousList = readExperienceRepository.findByLoginId(loginId);
        readExperienceRepository.deleteAll(previousList);
        readExperienceRepository.flush();

        // 2. DTO로부터 업데이트 할 독서경험 리스트를 생성함
        List<String> bookIsbnList = readExperienceDTO.getBookIsbnList();

        List<ReadExperience> updatedList = new ArrayList<>();
        for (String isbn : bookIsbnList) {
            Book book = bookRepository.findOptionalByIsbn(isbn).get();
            ReadExperience readExperience = ReadExperience.builder().book(book).client(client).build();
            updatedList.add(readExperience);
        }

        // 3. 2번에서 생성한 독서경험 리스트를 저장함
        readExperienceRepository.saveAll(updatedList);
    }

    /**
     * 단일 책에 대한 독서경험을 추가할때 사용되는 서비스 코드
     * ex) 책 상세정보 페이지에서 '독서경험 추가하기', 한줄처방 페이지에서 '한줄처방 작성하기' 에서 사용된다.
     *
     * @return
     */
    @Transactional
    public ReadExperience createReadExperience(ReadExperienceCreateDto readExperienceCreateDto, AuthenticationAdapter authentication){

        String loginId = authentication.getUsername();
        Client client = clientRepository.findByLoginId(loginId).get();

        String isbn = readExperienceCreateDto.getBookIsbn();
        Book book = bookRepository.findOptionalByIsbn(isbn).get();

        ReadExperience readExperience = ReadExperience.builder().book(book).client(client).build();
        return readExperienceRepository.save(readExperience);
    }

    /**
     * 독서 경험을 페이징 하여 조회하는 서비스
     * */
    public Page<ReadExperienceResponseDto> getReadExperiences(AuthenticationAdapter authentication, Pageable pageable) {
        Client client = clientRepository.findByLoginId(authentication.getUsername()).get();
        String loginId = client.getLoginId();

        Page<ReadExperience> readExperiencePage = readExperienceRepository.findPagingByLoginId(loginId, pageable);

        Page<ReadExperienceResponseDto> readExperienceResponseDtoPage =
                readExperiencePage.map(readExperience -> new ReadExperienceResponseDto(readExperience));

        return readExperienceResponseDtoPage;
    }
}
