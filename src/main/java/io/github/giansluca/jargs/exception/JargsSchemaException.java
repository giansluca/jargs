package io.github.giansluca.jargs.exception;

public class JargsSchemaException extends JargsException {

    private final ErrorCode errorCode;
    private String errorParameter;

    public JargsSchemaException(ErrorCode errorCode, String errorParameter) {
        this.errorCode = errorCode;
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorParameter() {
        return errorParameter;
    }

    public void setErrorParameter(String errorParameter) {
        this.errorParameter = errorParameter;
    }

    public String getMessage() {
        switch (errorCode) {
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
