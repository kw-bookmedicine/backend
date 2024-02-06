package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.KWGraduate.BookPharmacy.dto.client.ClientJoinDto;
import kr.KWGraduate.BookPharmacy.dto.client.ClientLoginDto;
import kr.KWGraduate.BookPharmacy.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final ClientService clientService;

    @Operation(summary = "로그인", description = "id와 password로 로그인 확인", tags = {"login"})
    @PostMapping("/login")
    public ResponseEntity<ClientLoginDto> login(@Validated @RequestBody ClientLoginDto request,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = new StringBuffer("login validation error")
                    .append(" field: ").append(fieldError.getField())
                    .append(" code ").append(fieldError.getCode())
                    .append(" message: ").append(fieldError.getDefaultMessage())
                    .toString();
            System.out.println(errorMessage);

            return ResponseEntity.badRequest().build();
        }
        //front에서 해결가능?
        ClientLoginDto body = clientService.Login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(body);
    }
    @Operation(summary = "회원가입", description = "id, password, 이름, 닉네임, 이메일, 성별, 직업만 받도록 함", tags = {"signup"})
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@Validated @RequestBody ClientJoinDto clientJoinDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            FieldError fieldError = bindingResult.getFieldError();
            String errorMessage = new StringBuffer("signUp validation error")
                    .append(" field: ").append(fieldError.getField())
                    .append(" code ").append(fieldError.getCode())
                    .append(" message: ").append(fieldError.getDefaultMessage())
                    .toString();
            System.out.println(errorMessage);

            return ResponseEntity.badRequest().build();
        }
        clientService.signUp(clientJoinDto);
        return ResponseEntity.ok("success");
    }


}
