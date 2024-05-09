package kr.KWGraduate.BookPharmacy.global.common.error;

public class InvalidInputException extends BusinessException {

    public InvalidInputException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
}
