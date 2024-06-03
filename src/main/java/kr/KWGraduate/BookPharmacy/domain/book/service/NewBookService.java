package kr.KWGraduate.BookPharmacy.domain.book.service;

import kr.KWGraduate.BookPharmacy.domain.book.domain.NewBook;
import kr.KWGraduate.BookPharmacy.domain.book.dto.response.NewBookDto;
import kr.KWGraduate.BookPharmacy.domain.book.repository.NewBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewBookService {

    private final NewBookRepository newBookRepository;

    public List<NewBookDto> getNewBookList() {
        List<NewBook> allNewBooks = newBookRepository.findAllNewBooks();

        List<NewBookDto> dtoList = allNewBooks.stream().map(book -> NewBookDto.builder().newBook(book).build()).collect(Collectors.toList());

        return dtoList;
    }
}
