package org.gmdev.jargs.exception;

import static org.gmdev.jargs.exception.ErrorCode.OK;

public class JargsArgumentException extends JargsException {

    String errorArgumentName;
    ErrorCode errorCode = OK;

    public JargsArgumentException(String message) {
        super(message);
    }
}
