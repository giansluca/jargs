package org.gmdev.jargs.exception;

import static org.gmdev.jargs.exception.ErrorCode.OK;

public class JargsException extends Exception {

    ErrorCode errorCode = OK;

    public JargsException(String message) {
        super(message);
    }


}
