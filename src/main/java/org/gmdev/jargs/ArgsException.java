package org.gmdev.jargs;

public class ArgsException extends Exception {

    private char errorArgumentId = '\0';
    private String errorParameter = "TILT!";
    private ErrorCode errorCode = ErrorCode.OK;

    public ArgsException() {}

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgsException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ArgsException(ErrorCode errorCode, char errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public enum ErrorCode {
        OK("ok"),
        MISSING_STRING("missing string"),
        MISSING_INTEGER("missing integer"),
        INVALID_INTEGER("invalid integer"),
        UNEXPECTED_ARGUMENT("unexpected argument"),
        MISSING_DOUBLE("missing double"),
        INVALID_DOUBLE("invalid double"),
        TEST_CODE("test code");

        private final String description;

        ErrorCode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
