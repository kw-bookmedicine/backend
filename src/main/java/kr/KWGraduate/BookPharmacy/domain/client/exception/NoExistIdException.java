package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.ResourceNotFoundException;

public class NoExistIdException extends ResourceNotFoundException {

    public NoExistIdException(String target){
        super("there is not such id : " + target);
    }
}
