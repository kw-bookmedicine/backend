package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.dto.client.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.request.OneLineCreateDto;
import kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.request.OneLineUpdateDto;
import kr.KWGraduate.BookPharmacy.dto.oneLinePrescription.response.OneLineResponseDto;
import kr.KWGraduate.BookPharmacy.entity.OneLinePrescription;
import kr.KWGraduate.BookPharmacy.service.OneLinePrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/oneline-prescriptions")
@RequiredArgsConstructor
@Tag(name = "한줄처방 api")
public class OneLinePrescriptionController {

    private final OneLinePrescriptionService oneLinePrescriptionService;

    @Operation(summary = "모든 한줄처방 리스트를 페이징하여 요청", description = "page와 size를 입력받아 이를 반환")
    @GetMapping("/all")
    public ResponseEntity<Page<OneLineResponseDto>> getAllOneLinePrescriptions(@RequestParam(name = "page") int page,
                                                                               @RequestParam(name = "size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OneLineResponseDto> result = oneLinePrescriptionService.getAllOneLinePrescriptions(pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "나의 한줄처방 리스트를 페이징하여 요청", description = "page와 size를 입력받아 이를 반환")
    @GetMapping("/my")
    public ResponseEntity<Page<OneLineResponseDto>> getMyOneLinePrescriptions(@RequestParam(name = "page") int page,
                                                                              @RequestParam(name = "size") int size,
                                                                              @AuthenticationPrincipal UserDetails userDetails) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<OneLineResponseDto> result = oneLinePrescriptionService.getMyOneLinePrescriptions((AuthenticationAdapter) userDetails, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "한줄처방을 생성하도록 요청")
    @PostMapping("/new")
    public ResponseEntity<Void> createOneLinePrescription(@RequestBody OneLineCreateDto oneLineCreateDto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        oneLinePrescriptionService.createOneLinePrescription(oneLineCreateDto, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "한줄처방을 단건으로 조회할 때 요청")
    @GetMapping("/{prescriptionId}")
    public ResponseEntity<OneLineResponseDto> getOneLinePrescription(@PathVariable(value = "prescriptionId") Long prescriptionId) {
        OneLineResponseDto result = oneLinePrescriptionService.getOneLinePrescription(prescriptionId);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "한줄처방을 수정할때 요청")
    @PutMapping("/{prescriptionId}")
    public ResponseEntity<Void> updateOneLinePrescription(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                          @RequestBody OneLineUpdateDto oneLineUpdateDto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        oneLinePrescriptionService.updateOneLinePrescription(prescriptionId, oneLineUpdateDto, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "한줄처방을 삭제할 때 요청")
    @DeleteMapping("/{prescriptionId}")
    public ResponseEntity<Void> deleteOneLinePrescription(@PathVariable(value = "prescriptionId") Long prescriptionId,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        oneLinePrescriptionService.deleteOneLinePrescription(prescriptionId, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok().build();
    }

}