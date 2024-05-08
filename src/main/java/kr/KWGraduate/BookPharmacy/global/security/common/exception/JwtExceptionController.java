package kr.KWGraduate.BookPharmacy.global.security.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import kr.KWGraduate.BookPharmacy.global.common.error.ErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleAccessDeniedException(AccessDeniedException ex){
        ErrorResult errorResult = new ErrorResult("cant access because not proper role", ex.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleInvalidSignatureException(SignatureException ex){
        ErrorResult errorResult = new ErrorResult("token is invalid", ex.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResult> handleMalformedJwtException(MalformedJwtException ex){
        ErrorResult errorResult = new ErrorResult("token is malformed",ex.getMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleJwtException(ExpiredJwtException ex){
        ErrorResult errorResult = new ErrorResult("token is expired", ex.getMessage());

        return new ResponseEntity<>(errorResult,HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResult> handleJwtException(JwtException ex){
        ErrorResult errorResult = new ErrorResult("token is null or token is not started Bearer", ex.getMessage());

        return new ResponseEntity<>(errorResult,HttpStatus.FORBIDDEN);

    }




}


