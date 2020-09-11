package io.github.giansluca.jargs.exception;

public enum ErrorCode {
    OK("ok"),
    INVALID_SCHEMA_ELEMENT_TYPE("Invalid schema element type"),
    EMPTY_SCHEMA_ELEMENT_NAME("Empty schema element name"),
    INVALID_SCHEMA_ELEMENT_NAME("Invalid schema name"),
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
