package io.github.giansluca.jargs.exception;

public class JargsArgumentException extends JargsException {

    private final ErrorCode errorCode;
    private String errorArgumentName;
    private String errorParameter;

    public JargsArgumentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public JargsArgumentException(
            ErrorCode errorCode, String errorArgumentName, String errorParameter) {

        this.errorCode = errorCode;
        this.errorArgumentName = errorArgumentName;
        this.errorParameter = errorParameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
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

    @Override
    public String getMessage() {
        return switch (errorCode) {
            case UNEXPECTED_ARGUMENT -> String.format("Argument name -%s unexpected.", errorArgumentName);
            case INVALID_ARGUMENT_NAME -> String.format("'%s' is not a valid argument name.", errorArgumentName);
            case MISSING_STRING -> String.format("Could not find string argument value for '-%s' .", errorArgumentName);
            case MISSING_INTEGER ->
                    String.format("Could not find integer argument value for '-%s' .", errorArgumentName);
            case INVALID_INTEGER -> String.format(
                    "Argument name '-%s' expects an integer but was '%s' .", errorArgumentName, errorParameter);
            case MISSING_DOUBLE -> String.format("Could not find double argument value for '-%s' .", errorArgumentName);
            case INVALID_DOUBLE -> String.format(
                    "Argument name '-%s' expects a double but was '%s' .", errorArgumentName, errorParameter);
            default -> "";
        };
    }

}
