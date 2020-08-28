package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {

    private int intValue = 0;

    @Override
    public void set(Iterator<String> currentArgument) throws ArgsException {
        String parameter;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            //errorCode = ArgsException.ErrorCode.MISSING_INTEGER;
            throw new ArgsException();
        } catch (NumberFormatException e) {
            //errorParameter = parameter;
            //errorCode = ArgsException.ErrorCode.INVALID_INTEGER;
            throw new ArgsException();
        }
    }

    @Override
    public Object get() {
        return intValue;
    }


}
