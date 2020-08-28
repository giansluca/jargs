package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArgumentMarshaler extends ArgumentMarshaler {

    private String stringValue = "";

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            //errorCode = ArgsException.ErrorCode.MISSING_STRING;
            throw new ArgsException();
        }
    }

    @Override
    public Object get() {
        return stringValue;
    }


}
