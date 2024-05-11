package kr.KWGraduate.BookPharmacy.global.common.error;

public class AlreadyExistResourceException extends BusinessException {
    public AlreadyExistResourceException(String message) {
        super(message, ErrorCode.ALREADY_EXIST_RESOURCE);
    }
}
