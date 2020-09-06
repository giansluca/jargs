package org.gmdev.jargs;

import static org.gmdev.jargs.ArgsException.ErrorCode.OK;

public class ArgsException extends Exception {

    private String errorArgumentId = "";
    private String errorParameter;
    private ErrorCode errorCode = OK;

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

    public ArgsException(ErrorCode errorCode, String errorArgumentId, String errorParameter) {
        this.errorCode = errorCode;
        this.errorArgumentId = errorArgumentId;
        this.errorParameter = errorParameter;
    }

    public String getErrorArgumentId() {
        return errorArgumentId;
    }

    public void setErrorArgumentId(String errorArgumentId) {
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
                return String.format("Argument -%s unexpected", errorArgumentId);
            case INVALID_ARGUMENT_NAME:
                return String.format("'%s' is not a valid argument name", errorArgumentId);
            case INVALID_FORMAT:
                return String.format("'%s' is not a valid argument parameter", errorParameter);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%s", errorArgumentId);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%s", errorArgumentId);
            case INVALID_INTEGER:
                return String.format(
                        "Argument -%s expects an integer but was '%s'", errorArgumentId, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -%s", errorArgumentId);
            case INVALID_DOUBLE:
                return String.format(
                        "Argument -%s expects a double but was '%s'", errorArgumentId, errorParameter);
            default:
                return "";
        }
    }

    public enum ErrorCode {
        OK("ok"),
        NO_CODE("No code"),
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
