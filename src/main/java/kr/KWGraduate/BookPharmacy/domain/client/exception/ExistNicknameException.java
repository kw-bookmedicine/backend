package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AlreadyExistResourceException;
import kr.KWGraduate.BookPharmacy.global.common.error.BusinessException;

public class ExistNicknameException extends AlreadyExistResourceException {

    public ExistNicknameException(String target) {
        super("nickname : " + target + " is already exist");
    }
}
