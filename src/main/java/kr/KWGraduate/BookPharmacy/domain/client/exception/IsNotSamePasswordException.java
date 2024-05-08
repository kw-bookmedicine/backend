package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AllException;

public class IsNotSamePasswordException extends AllException {

    public IsNotSamePasswordException(String errorMessage){
        super(errorMessage);
    }
}
