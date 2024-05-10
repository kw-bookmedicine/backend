package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AlreadyExistResourceException;

public class ExistIdException extends AlreadyExistResourceException {

    public ExistIdException(String target) {
        super("id : " + target + " is already exist");
    }
}
