package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.ResourceNotFoundException;

public class IsNotSamePasswordException extends ResourceNotFoundException {

    public IsNotSamePasswordException(String target){
        super("password is not same");
    }
}
