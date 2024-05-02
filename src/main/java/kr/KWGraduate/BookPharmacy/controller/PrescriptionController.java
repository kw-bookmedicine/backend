package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.client.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.dto.prescription.request.PrescriptionCreateDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.request.PrescriptionModifyDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.response.PrescriptionBoardPageDto;
import kr.KWGraduate.BookPharmacy.dto.prescription.response.PrescriptionMyPageDto;
import kr.KWGraduate.BookPharmacy.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public List<PrescriptionBoardPageDto> getPrescription(
            @RequestParam("size") int size,
            @RequestParam("page") int page,
            @RequestParam("boardId") Long boardId
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
        return prescriptionService.getPrescriptions(pageRequest, boardId);
    }

    @GetMapping("/my")
    @Operation(summary = "게시물에 대한 처방전 조회", description = "무한 스크롤 사용으로 인해 page와 size입력")
    public List<PrescriptionMyPageDto> getPrescription(
            @RequestParam("size") int size,
            @RequestParam("page") int page,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdDate"));
        return prescriptionService.getPrescriptions(pageRequest, (AuthenticationAdapter) userDetails);
    }


    @PostMapping
    @Operation(summary = "처방전 생성")
    public void createPrescription(@RequestBody PrescriptionCreateDto prescriptionCreateDto, @AuthenticationPrincipal UserDetails userDetails) {
        prescriptionService.createPrescription(prescriptionCreateDto, (AuthenticationAdapter) userDetails);
    }

    @PutMapping("/{prescriptionId}")
    @Operation(summary = "처방전 수정")
    public void modifyPrescription(@PathVariable("prescriptionId") Long prescriptionId,@RequestBody PrescriptionModifyDto prescriptionModifyDto){
        prescriptionService.modifyPrescription(prescriptionId,prescriptionModifyDto);
    }
    @DeleteMapping("/{prescriptionId}")
    @Operation(summary = "처방전 삭제")
    public void deletePrescription(@PathVariable("prescriptionId") Long prescriptionId){
        prescriptionService.deletePrescription(prescriptionId);
    }
}
