package org.gmdev.jargs.marshalers;

public class StringArgumentMarshaler extends ArgumentMarshaler {

    private String stringValue = "";

    @Override
    public void set(String value) {
        stringValue = value;
    }

    @Override
    public Object get() {
        return stringValue;
    }


}
