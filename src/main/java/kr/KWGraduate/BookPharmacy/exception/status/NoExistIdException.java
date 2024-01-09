package kr.KWGraduate.BookPharmacy.exception.status;

import java.util.NoSuchElementException;

public class NoExistIdException extends AllException{
    public NoExistIdException(String errorMessage){
        super(errorMessage);
    }
}
