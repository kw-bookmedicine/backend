package kr.KWGraduate.BookPharmacy.exception.status;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends AllException{

    public ResourceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
