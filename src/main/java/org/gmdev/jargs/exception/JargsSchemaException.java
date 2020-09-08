package org.gmdev.jargs.exception;

import static org.gmdev.jargs.exception.ErrorCode.OK;

public class JargsSchemaException extends JargsException {

    String errorSchemaElement;
    String errorSchemaElementName;
    String errorSchemaElementType;
    ErrorCode errorCode = OK;

    public JargsSchemaException(String message) {
        super(message);
    }





}
