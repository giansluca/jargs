package org.gmdev.jargs.exception;

import static org.gmdev.jargs.exception.ErrorCode.OK;

public class JargsArgumentException extends JargsException {

    ErrorCode errorCode = OK;
    String errorArgumentName;
    String errorParameter;

    public JargsArgumentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public JargsArgumentException(
            ErrorCode errorCode, String errorArgumentName, String errorParameter) {

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

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: Should not get here.");
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument name -%s unexpected.", errorArgumentName);
            case INVALID_ARGUMENT_NAME:
                return String.format("'%s' is not a valid argument name.", errorArgumentName);
            case MISSING_STRING:
                return String.format("Could not find string argument value for -%s.", errorArgumentName);
            case MISSING_INTEGER:
                return String.format("Could not find integer argument value for -%s.", errorArgumentName);
            case INVALID_INTEGER:
                return String.format(
                        "Argument name -%s expects an integer but was '%s'.", errorArgumentName, errorParameter);
            case MISSING_DOUBLE:
                return String.format("Could not find double argument value for -%s.", errorArgumentName);
            case INVALID_DOUBLE:
                return String.format(
                        "Argument name -%s expects a double but was '%s'.", errorArgumentName, errorParameter);
            default:
                return "";
        }
    }
}
