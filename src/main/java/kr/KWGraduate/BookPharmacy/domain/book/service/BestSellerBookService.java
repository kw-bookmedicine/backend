package kr.KWGraduate.BookPharmacy.domain.book.service;

import kr.KWGraduate.BookPharmacy.domain.book.domain.BestSellerBook;
import kr.KWGraduate.BookPharmacy.domain.book.domain.Book;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BestSellerBookDto;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.ReadExperienceTop10Dto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BestSellerBookRepository;
import kr.KWGraduate.BookPharmacy.domain.book.repository.BookRepository;
import kr.KWGraduate.BookPharmacy.domain.readexperience.repository.ReadExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BestSellerBookService {

    private final BestSellerBookRepository bestSellerBookRepository;
    private final ReadExperienceRepository readExperienceRepository;
    private final BookRepository bookRepository;

    public List<BestSellerBookDto> getBestSellerBookList() {
        List<BestSellerBook> allBestSeller = bestSellerBookRepository.findAllBestSeller();

        List<BestSellerBookDto> dtoList = allBestSeller.stream().map(book -> BestSellerBookDto.builder().bestSellerBook(book).build())
                .collect(Collectors.toList());

        return dtoList;
    }

    // 스프링 배치 프로그램으로 돌아감
    @Transactional
    public void setBestSellerBooks() {

        // 사람들이 가장 많이 읽었던 책 10권을 가져옴
        PageRequest pageRequest = PageRequest.of(0, 10);
        List<ReadExperienceTop10Dto> top10ReadExperiences = readExperienceRepository.findTop10ReadExperiences(pageRequest);


        // 상위 10권에 대하여 BestSellerBook 엔티티 리스트로 변환함.
        List<BestSellerBook> newBestSellerBooks = new ArrayList<>();
        for (ReadExperienceTop10Dto readExperienceTop10Dto : top10ReadExperiences ) {
            Long bookId = readExperienceTop10Dto.getBookId();
            Book book = bookRepository.findById(bookId).get();

            BestSellerBook bestSellerBook = BestSellerBook.builder().book(book).build();

            newBestSellerBooks.add(bestSellerBook);
        }

        // 기존 베스트셀러 목록을 지우고, 새로운 베스트셀러 목록을 저장함
        bestSellerBookRepository.deleteAll();
        bestSellerBookRepository.flush();
        bestSellerBookRepository.saveAll(newBestSellerBooks);
    }
}
