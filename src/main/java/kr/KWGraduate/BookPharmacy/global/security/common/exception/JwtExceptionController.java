package kr.KWGraduate.BookPharmacy.global.security.common.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import kr.KWGraduate.BookPharmacy.global.common.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JwtExceptionController {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex){
        ErrorResponse errorResponse = new ErrorResponse("cant access because not proper role", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidSignatureException(SignatureException ex){
        ErrorResponse errorResponse = new ErrorResponse("token is invalid", ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);

    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex){
        ErrorResponse errorResponse = new ErrorResponse("token is malformed",ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleJwtException(ExpiredJwtException ex){
        ErrorResponse errorResponse = new ErrorResponse("token is expired", ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);

    }
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex){
        ErrorResponse errorResponse = new ErrorResponse("token is null or token is not started Bearer", ex.getMessage());

        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);

    }




}


