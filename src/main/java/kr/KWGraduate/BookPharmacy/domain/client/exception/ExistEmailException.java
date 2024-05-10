package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AlreadyExistResourceException;

public class ExistEmailException extends AlreadyExistResourceException {

    public ExistEmailException(String target) {
        super("email : " + target + " is already exist");
    }
}
