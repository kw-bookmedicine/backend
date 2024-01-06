package kr.KWGraduate.BookPharmacy.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerTestController {

    @Operation(summary = "태스트", description = "테스트하려고 대충 만듦", tags = {"hello"})
    @GetMapping(value = "/hello")
    public String hello(){
        return "hello!!!!test";
    }
}
