package kr.KWGraduate.BookPharmacy.domain.book.api;

import kr.KWGraduate.BookPharmacy.domain.book.dto.response.BestSellerBookDto;
import kr.KWGraduate.BookPharmacy.domain.book.service.BestSellerBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/best-seller")
@RequiredArgsConstructor
public class BestSellerBookController {

    private final BestSellerBookService bestSellerBookService;

    @GetMapping()
    public ResponseEntity<List<BestSellerBookDto>> getBestSellerBooks() {
        List<BestSellerBookDto> result = bestSellerBookService.getBestSellerBookList();

        return ResponseEntity.ok(result);
    }
}
