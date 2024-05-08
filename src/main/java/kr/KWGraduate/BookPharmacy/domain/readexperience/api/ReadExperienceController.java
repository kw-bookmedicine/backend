package kr.KWGraduate.BookPharmacy.domain.readexperience.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request.ReadExperienceCreateDto;
import kr.KWGraduate.BookPharmacy.domain.readexperience.dto.request.ReadExperienceUpdateRequestDto;
import kr.KWGraduate.BookPharmacy.domain.readexperience.dto.response.ReadExperienceResponseDto;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import kr.KWGraduate.BookPharmacy.domain.readexperience.service.ReadExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experiences")
@RequiredArgsConstructor
@Tag(name = "독서경험 api")
public class ReadExperienceController {

    private final ReadExperienceService readExperienceService;


    @Operation(summary = "유저의 독서경험 리스트를 요청", description = "page와 size를 입력받아 이를 반환")
    @GetMapping("/list")
    public ResponseEntity<Page<ReadExperienceResponseDto>> getReadExperiences(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size,
                                                                              @AuthenticationPrincipal UserDetails userDetails) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ReadExperienceResponseDto> result = readExperienceService.getReadExperiences((AuthenticationAdapter) userDetails, pageRequest);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "유저의 독서경험 리스트를 일괄적으로 업데이트", description = "updateDto를 입력받아, 독서경험 리스트를 업데이트")
    @PostMapping("/list")
    public ResponseEntity<Void> updateReadExperiences(@RequestBody ReadExperienceUpdateRequestDto updateDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        readExperienceService.updateReadExperience(updateDto, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "독서경험을 단건으로 추가할때 요청")
    @PostMapping("/new")
    public ResponseEntity<Void> createReadExperiences(@RequestBody ReadExperienceCreateDto createDto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        readExperienceService.createReadExperience(createDto, (AuthenticationAdapter) userDetails);

        return ResponseEntity.ok().build();
    }
}
