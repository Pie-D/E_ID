package vn.cmcati.eid.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AppException extends RuntimeException {

    ErrorCode errorCode;
    public AppException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
