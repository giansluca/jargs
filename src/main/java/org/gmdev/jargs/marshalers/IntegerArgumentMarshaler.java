package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {

    private int intValue = 0;

    @Override
    public void set(String value) throws ArgsException {
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new ArgsException();
        }
    }

    @Override
    public Object get() {
        return intValue;
    }


}
