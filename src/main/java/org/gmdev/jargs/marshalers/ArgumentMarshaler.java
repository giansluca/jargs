package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.ArgsException;

public abstract class ArgumentMarshaler {

    public abstract void set(String value) throws ArgsException;

    public abstract Object get();

}
