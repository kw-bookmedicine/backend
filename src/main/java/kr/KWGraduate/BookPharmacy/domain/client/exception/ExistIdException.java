package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AllException;

public class ExistIdException extends AllException {
    public ExistIdException(String errorMessage){
        super(errorMessage);
    }
}
