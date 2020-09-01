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

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: Should not get here");
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpected", errorArgumentId);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c", errorArgumentId);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c", errorArgumentId);
            case INVALID_INTEGER:
                return String.format(
                        "Argument -%c expects an integer but was '%s'", errorArgumentId, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -%c", errorArgumentId);
            case INVALID_DOUBLE:
                return String.format(
                        "Argument -%c expects a double but was '%s'", errorArgumentId, errorParameter);
        }

        return "";
    }

    public enum ErrorCode {
        OK("ok"),
        INVALID_FORMAT("Invalid format"),
        UNEXPECTED_ARGUMENT("Unexpected argument"),
        INVALID_ARGUMENT_NAME("Invalid argument name"),
        MISSING_STRING("Missing string"),
        MISSING_INTEGER("Missing integer"),
        INVALID_INTEGER("Invalid integer"),
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
