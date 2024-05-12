package kr.KWGraduate.BookPharmacy.domain.prescription.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.request.PrescriptionCreateDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.request.PrescriptionModifyDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.response.PrescriptionBoardPageDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.dto.response.PrescriptionMyPageDto;
import kr.KWGraduate.BookPharmacy.domain.prescription.service.PrescriptionService;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/prescription")
@Tag(name = "처방전 api")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    @GetMapping
    @Operation(summary = "게시물에 대한 처방전 조회", description = "무한 스크롤 사용으로 인해 page와 size입력")
    public ResponseEntity<List<PrescriptionBoardPageDto>> getPrescription(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("boardId") Long boardId
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
        return ResponseEntity.ok(prescriptionService.getPrescriptions(pageRequest, boardId));
    }

    @GetMapping("/my")
    @Operation(summary = "나의 처방전 조회", description = "무한 스크롤 사용으로 인해 page와 size입력")
    public ResponseEntity<List<PrescriptionMyPageDto>> getPrescription(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
        return ResponseEntity.ok(prescriptionService.getPrescriptions(pageRequest, (AuthenticationAdapter) userDetails));
    }


    @PostMapping
    @Operation(summary = "처방전 생성")
    public ResponseEntity<String> createPrescription(@RequestBody PrescriptionCreateDto prescriptionCreateDto, @AuthenticationPrincipal UserDetails userDetails) {
        prescriptionService.createPrescription(prescriptionCreateDto, (AuthenticationAdapter) userDetails);
        return ResponseEntity.ok("success");
    }

    @PutMapping("/{prescriptionId}")
    @Operation(summary = "처방전 수정")
    public ResponseEntity<String> modifyPrescription(@PathVariable("prescriptionId") Long prescriptionId,@RequestBody PrescriptionModifyDto prescriptionModifyDto){
        prescriptionService.modifyPrescription(prescriptionId,prescriptionModifyDto);
        return ResponseEntity.ok("success");
    }
    @DeleteMapping("/{prescriptionId}")
    @Operation(summary = "처방전 삭제")
    public ResponseEntity<String> deletePrescription(@PathVariable("prescriptionId") Long prescriptionId){
        prescriptionService.deletePrescription(prescriptionId);
        return ResponseEntity.ok("success");
    }
}