package kr.KWGraduate.BookPharmacy.domain.client.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.AllException;

public class ExistNicknameException extends AllException {
    public ExistNicknameException(String errorMesssage){
        super(errorMesssage);
    }
}
