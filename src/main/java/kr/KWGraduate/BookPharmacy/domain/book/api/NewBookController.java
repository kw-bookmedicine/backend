package kr.KWGraduate.BookPharmacy.domain.book.api;

import kr.KWGraduate.BookPharmacy.domain.book.dto.response.NewBookDto;
import kr.KWGraduate.BookPharmacy.domain.book.service.NewBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/new-book")
@RequiredArgsConstructor
public class NewBookController {

    private final NewBookService newBookService;

    @GetMapping()
    public ResponseEntity<List<NewBookDto>> getBestSellerBooks() {
        List<NewBookDto> result = newBookService.getNewBookList();

        return ResponseEntity.ok(result);
    }
}
