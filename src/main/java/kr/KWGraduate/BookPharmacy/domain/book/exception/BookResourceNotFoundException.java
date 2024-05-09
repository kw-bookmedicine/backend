package kr.KWGraduate.BookPharmacy.domain.book.exception;

import kr.KWGraduate.BookPharmacy.global.common.error.ResourceNotFoundException;

public class BookResourceNotFoundException extends ResourceNotFoundException {

    public BookResourceNotFoundException(String target){
        super("there is not such isbn : " + target);
    }
}
