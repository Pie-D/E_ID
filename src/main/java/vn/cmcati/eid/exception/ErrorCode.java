package vn.cmcati.eid.exception;

import lombok.Getter;


@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized Exception"),
    USERNAME_EXISTED(101, "Username already existed"),
    EMAIL_EXISTED(107, "Email already existed"),
    NOT_FOUND(105, "Not found"),
    INVALID_KEY(111, "Invalid message key"),
    USERNAME_INVALID(102, "Username must be at least 3 characters"),
    PASSWORD_INVALID(103, "Password must be at least 8 characters"),
    UNAUTHENTICATED(104, "Unauthenticated"),
    OCR_PROCESSING_ERROR(901, "Error processing OCR request"),
    OCR_API_ERROR(900, "OCR API Error with status {status} and message: {message}"),
    FACE_MATCHING_PROCESSING_ERROR(902, "Error processing Face matching request"),
    EKYC_PROCESSING_ERROR(903, "Error processing EKYC request"),
    INVALID_TOKEN(904, "Invalid token"),
    TOKEN_EXPIRED(905, "Token has expired"),
    OUT_OF_REQUEST(906, "Out of request"),
    ;
    private int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
