package org.gmdev.jargs.exception;

import static org.gmdev.jargs.exception.ErrorCode.OK;

public class JargsSchemaException extends JargsException {

    private ErrorCode errorCode = OK;
    private String errorParameter;

    public JargsSchemaException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: Should not get here.");
            case INVALID_SCHEMA_ELEMENT_TYPE:
                return String.format("'%s' is not a valid schema element type.", errorParameter);
            case EMPTY_SCHEMA_ELEMENT_NAME:
                return String.format("'%s' is not a valid schema, element name cannot be empty.", errorParameter);
            case INVALID_SCHEMA_ELEMENT_NAME:
                return String.format("'%s' is not a valid schema element name.", errorParameter);
            default:
                return "";
        }
    }

}
