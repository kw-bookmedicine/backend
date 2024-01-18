package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.BookDto;
import kr.KWGraduate.BookPharmacy.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "책 상세정보 출력", description = "isbn으로 책 상세정보 조회하기(parameter로 isbn 입력")
    @GetMapping(value = "/detail")
    public ResponseEntity<BookDto> getBookDetails(@RequestParam(name ="isbn") String isbn){
        BookDto result = bookService.getBookDetails(isbn);

        return ResponseEntity.ok(result);
    }
}
