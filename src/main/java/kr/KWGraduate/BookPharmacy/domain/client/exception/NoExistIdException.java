package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AllException;

public class NoExistIdException extends AllException {
    public NoExistIdException(String errorMessage){
        super(errorMessage);
    }
}
