package kr.KWGraduate.BookPharmacy.domain.onelineprescription.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.service.OneLineHelpfulEmotionService;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.service.OneLineLikeEmotionService;
import kr.KWGraduate.BookPharmacy.domain.onelineprescription.service.OneLinePrescriptionService;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oneline-emotion")
@RequiredArgsConstructor
@Tag(name = "한줄처방 이모션 api")
public class OneLineEmotionController {

    private final OneLineHelpfulEmotionService oneLineHelpfulEmotionService;
    private final OneLineLikeEmotionService oneLineLikeEmotionService;

    @Operation(summary = "한줄처방 '좋아요' 추가")
    @GetMapping("like/{prescriptionId}")
    public ResponseEntity<String> createLikeEmotion(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                    @AuthenticationPrincipal AuthenticationAdapter userDetails) {
        oneLineLikeEmotionService.insert(prescriptionId, userDetails);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "한줄처방 '좋아요' 제거")
    @DeleteMapping("like/{prescriptionId}")
    public ResponseEntity<String> deleteLikeEmotion(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                    @AuthenticationPrincipal AuthenticationAdapter userDetails) {
        oneLineLikeEmotionService.delete(prescriptionId, userDetails);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "한줄처방 '도움이 되었어요' 추가")
    @GetMapping("helpful/{prescriptionId}")
    public ResponseEntity<String> createHelpfulEmotion(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                    @AuthenticationPrincipal AuthenticationAdapter userDetails) {
        oneLineHelpfulEmotionService.insert(prescriptionId, userDetails);

        return ResponseEntity.ok("success");
    }

    @Operation(summary = "한줄처방 '도움이 되었어요' 제거")
    @DeleteMapping("helpful/{prescriptionId}")
    public ResponseEntity<String> deleteHelpfulEmotion(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                    @AuthenticationPrincipal AuthenticationAdapter userDetails) {
        oneLineHelpfulEmotionService.delete(prescriptionId, userDetails);

        return ResponseEntity.ok("success");
    }


}
