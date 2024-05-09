package kr.KWGraduate.BookPharmacy.global.common.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    RESOURCE_NOT_FOUND(400, "C002", "Resource Not Found"),
    ALREADY_EXIST_RESOURCE(400, "C003", "Already Exist Resource"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    HANDLE_ACCESS_DENIED(403, "C005", "Access Denied")
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
