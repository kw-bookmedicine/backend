package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.ErrorCode;
import kr.KWGraduate.BookPharmacy.global.common.error.ErrorResponse;
import kr.KWGraduate.BookPharmacy.global.common.error.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionController {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleNoExistIdException(NoExistIdException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(ErrorCode.RESOURCE_NOT_FOUND.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIsNotSamePasswordException(IsNotSamePasswordException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(ErrorCode.ALREADY_EXIST_RESOURCE.getStatus()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAlreadyExistIdException(ExistIdException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(ErrorCode.ALREADY_EXIST_RESOURCE.getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAlreadyExistEmailException(ExistEmailException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(ErrorCode.ALREADY_EXIST_RESOURCE.getStatus()));
    }
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleAlreadyExistIdException(ExistNicknameException ex){
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode().getCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(ErrorCode.ALREADY_EXIST_RESOURCE.getStatus()));
    }
}

