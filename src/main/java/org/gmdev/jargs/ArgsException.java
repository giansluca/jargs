package org.gmdev.jargs;

public class ArgsException extends Exception {

    private char errorArgumentId = '\0';
    private String errorParameter;
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

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c", errorArgumentId);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Invalid integer parameter for -%c", errorArgumentId);
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpected", errorArgumentId);
            case OK:
                throw new Exception("TILT: Should not get here");
        }

        return "";
    }

    public enum ErrorCode {
        OK("ok"),
        INVALID_ARGUMENT_NAME("Invalid argument name"),
        INVALID_FORMAT("Invlid format"),
        MISSING_STRING("Missing string"),
        MISSING_INTEGER("Missing integer"),
        INVALID_INTEGER("Invalid integer"),
        UNEXPECTED_ARGUMENT("Unexpected argument"),
        MISSING_DOUBLE("Missing double"),
        INVALID_DOUBLE("Invalid double");

        private final String description;

        ErrorCode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
