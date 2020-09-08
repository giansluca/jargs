package org.gmdev.jargs;

import org.gmdev.jargs.exception.ErrorCode;

import static org.gmdev.jargs.exception.ErrorCode.*;

public class ArgsException extends Exception {

    private String errorArgumentName = "";
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

    public ArgsException(ErrorCode errorCode, String errorArgumentName, String errorParameter) {
        this.errorCode = errorCode;
        this.errorArgumentName = errorArgumentName;
        this.errorParameter = errorParameter;
    }

    public String getErrorArgumentName() {
        return errorArgumentName;
    }

    public void setErrorArgumentName(String errorArgumentName) {
        this.errorArgumentName = errorArgumentName;
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
            case INVALID_SCHEMA_ELEMENT_TYPE:
                return String.format("'%s' is not a valid schema element type", errorParameter);
            case EMPTY_SCHEMA_ELEMENT_NAME:
                return String.format("Schema element name is empty: '%s'", errorParameter);
            case INVALID_SCHEMA_ELEMENT_NAME:
                return String.format("'%s' is not a valid schema element name", errorParameter);
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%s unexpected", errorArgumentName);
            case INVALID_ARGUMENT_NAME:
                return String.format("'%s' is not a valid argument name", errorArgumentName);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%s", errorArgumentName);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%s", errorArgumentName);
            case INVALID_INTEGER:
                return String.format(
                        "Argument -%s expects an integer but was '%s'", errorArgumentName, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double parameter for -%s", errorArgumentName);
            case INVALID_DOUBLE:
                return String.format(
                        "Argument -%s expects a double but was '%s'", errorArgumentName, errorParameter);
            default:
                return "";
        }
    }

}
