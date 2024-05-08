package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AllException;

public class ExistEmailException extends AllException {
    public ExistEmailException(String errorMessage){
        super(errorMessage);
    }
}
