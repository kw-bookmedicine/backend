package kr.KWGraduate.BookPharmacy.domain.book.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.book.service.RecommendService;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommend/book")
@Tag(name = "책 추천 api")
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/clientbased/aiprescription")
    @Operation(summary = "메인 페이지에 출력되는 ai처방 3개", description = "아이디: string, password: string만 가능, 로그인 필수")
    public ResponseEntity<?> getClientBasedAiPrescription(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(recommendService.getClientBasedAiPrescription((AuthenticationAdapter) userDetails));
    }

    @GetMapping("/clientbased")
    @Operation(summary = "메인 페이지에 출력되는 비슷한 유저 기반 책 추천 10개", description = "아이디: string, password: string만 가능,로그인 필수")
    public ResponseEntity<?> getClientBasedRecommend(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(recommendService.getClientBasedRecommend((AuthenticationAdapter) userDetails));
    }

    @GetMapping("/boardbased")
    @Operation(summary = "고민 게시판 ai처방", description = "예시 boardId는 167임. 키워드(max=3) 넘겨주면 description은 프론트에서 구현해야함,<br> 다른 값들이 null일때, recommending이 true이면 추천 중이라는 의미, recommending이 false이면 추천할 책이 없다는 의미")
    public ResponseEntity<?> getBoardBasedRecommend(@RequestParam("boardId") Long boardId){
        return ResponseEntity.ok(recommendService.getBoardBasedRecommend(boardId));
    }

    @GetMapping("/bookbased")
    @Operation(summary = "연관 책 리스트", description = "예시 isbn은 9788901126050임. 10개 넘겨짐")
    public ResponseEntity<?> getBookBasedRecommend(@RequestParam("isbn") String isbn){
        return ResponseEntity.ok(recommendService.getBookBasedRecommend(isbn));
    }
}
