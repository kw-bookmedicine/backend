package kr.KWGraduate.BookPharmacy.domain.client.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.KWGraduate.BookPharmacy.domain.client.dto.request.*;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientMainPageDto;
import kr.KWGraduate.BookPharmacy.domain.client.dto.response.ClientMypageDto;
import kr.KWGraduate.BookPharmacy.domain.client.service.ClientService;
import kr.KWGraduate.BookPharmacy.global.security.common.dto.AuthenticationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저 관련 api")
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

    @Operation(summary = "아이디 중복확인", description = "false이면 사용가능한 아이디, true이면 중복")
    @GetMapping("/duplicate/username")
    public ResponseEntity<Boolean> isExistUsername(@RequestParam("username") String username){
        return ResponseEntity.ok(clientService.isExistId(username));
    }
    @Operation(summary = "이메일 중복확인", description = "false이면 사용가능한 아이디, true이면 중복")
    @GetMapping("/duplicate/email")
    public ResponseEntity<Boolean> isExistEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(clientService.isExistEmail(email));
    }

    @Operation(summary = "닉네임 중복확인", description = "false이면 사용가능한 아이디, true이면 중복")
    @GetMapping("/duplicate/nickname")
    public ResponseEntity<Boolean> isExistNickname(@RequestParam("nickname") String nickname) {
        return ResponseEntity.ok(clientService.isExistNickname(nickname));
    }

    @Operation(summary = "자기소개와 직업정보 수정")
    @PutMapping("/client/info")
    public ResponseEntity<String> update(
            @RequestBody ClientUpdateDto clientUpdateDto,
            @AuthenticationPrincipal UserDetails userDetails){
        clientService.updateClient(clientUpdateDto,(AuthenticationAdapter) userDetails);
        return ResponseEntity.ok("success");
    }
    @Operation(summary = "비밀번호 수정")
    @PutMapping("/client/password")
    public ResponseEntity<String> updatePassword(
            @RequestBody ClientPasswordUpdateDto clientPasswordUpdateDto,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        clientService.updatePassword(clientPasswordUpdateDto,(AuthenticationAdapter) userDetails);
        return ResponseEntity.ok("success");
    }
    @Operation(summary = "닉네임 수정")
    @PutMapping("/client/nickname")
    public ResponseEntity<String> updateNickname(
            @RequestBody ClientNicknameDto clientNicknameDto,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        clientService.updateNickname(clientNicknameDto,(AuthenticationAdapter) userDetails);
        return ResponseEntity.ok("success");
    }

    @Operation(summary = "마이페이지의 회원정보 가져오기",description = "회원의 모든 정보 가져옴")
    @GetMapping("/client")
    public ResponseEntity<ClientMypageDto> getClient(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(clientService.getClient((AuthenticationAdapter) userDetails));
    }

    @Operation(summary = "메인페이지의 회원 정보 가져오기")
    @GetMapping("/client/main")
    public ResponseEntity<ClientMainPageDto> getMainPageClient(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(clientService.getMainPageClient((AuthenticationAdapter) userDetails));
    }

    @Operation(summary = "회원정보 탈퇴", description = "회원 탈퇴")
    @DeleteMapping("/client")
    public ResponseEntity<String> cancellation(@AuthenticationPrincipal UserDetails userDetails){
        clientService.cancellation((AuthenticationAdapter) userDetails);
        return ResponseEntity.ok("success");
    }



}
