package kr.KWGraduate.BookPharmacy.exception.controller;

import kr.KWGraduate.BookPharmacy.exception.ErrorResult;
import kr.KWGraduate.BookPharmacy.exception.status.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionController {
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleNotExistIdException(NoExistIdException ex){
        ErrorResult errorResult = new ErrorResult("NO Id", ex.getErrorMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleIsNotSamePasswordException(IsNotSamePasswordException ex){
        ErrorResult errorResult = new ErrorResult("Password Not Same", ex.getErrorMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleAlreadyExistIdException(ExistIdException ex){
        ErrorResult errorResult = new ErrorResult("there is already same Id", ex.getErrorMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleAlreadyExistEmailException(ExistEmailException ex){
        ErrorResult errorResult = new ErrorResult("there is already same email", ex.getErrorMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleAlreadyExistIdException(ExistNicknameException ex){
        ErrorResult errorResult = new ErrorResult("there is already same nickname", ex.getErrorMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResult> handleException(AllException ex){
        ErrorResult errorResult = new ErrorResult("error occur", ex.getErrorMessage());
        return new ResponseEntity<>(errorResult,HttpStatus.BAD_REQUEST);
    }
}

