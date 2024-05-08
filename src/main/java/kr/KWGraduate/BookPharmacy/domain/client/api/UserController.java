package kr.KWGraduate.BookPharmacy.domain.client.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientResponseDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.ClientUpdateDto;
import kr.KWGraduate.BookPharmacy.domain.client.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Client")
public class UserController {
    private final ClientService clientService;

    @Operation(summary = "로그인",description = "id와 password로 로그인")
    @PostMapping("/login")
    public void fakeLogin(@RequestBody ClientLoginDto request){
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @Operation(summary = "로그아웃" , description = "쿠키 유효기간 만료시킴")
    @GetMapping("/logout")
    public void fakeLogout(){
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }

    @Operation(summary = "회원가입" , description = "id, password, 이름, 닉네임, 이메일, 성별, 직업만 받도록 함")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ClientJoinDto clientJoinDto){
        clientService.signUp(clientJoinDto);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "회원정보 수정" , description = "password, 닉네임, 직업 수정")
    @PutMapping("/client")
    public ResponseEntity<String> update(@RequestBody ClientUpdateDto clientUpdateDto){
        clientService.updateClient(clientUpdateDto);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "본인의 회원정보 가져오기",description = "회원의 모든 정보 가져옴")
    @GetMapping("/client")
    public ResponseEntity<ClientResponseDto> getClient(){
        return ResponseEntity.ok(clientService.getClient());
    }

    @Operation(summary = "회원정보 탈퇴", description = "회원 탈퇴")
    @DeleteMapping("/client")
    public ResponseEntity<String> cancellation(){
        clientService.cancellation();
        return ResponseEntity.ok("success");
    }



}
