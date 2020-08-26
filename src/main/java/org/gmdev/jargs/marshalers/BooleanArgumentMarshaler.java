package org.gmdev.jargs.marshalers;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {

    private boolean booleanValue = false;

    @Override
    public void set(String value) {
        booleanValue = true;
    }

    @Override
    public Object get() {
        return booleanValue;
    }


}
