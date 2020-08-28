package org.gmdev.jargs;

public class ArgsException extends Exception {

    private char errorArgumentId = '\0';
    private String errorParameter = "TILT!";
    private ErrorCode errorCode = ErrorCode.OK;

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
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

        private final String errorCode;

        ErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorCode() {
            return errorCode;
        }
    }
}
