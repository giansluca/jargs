package org.gmdev.jargs.marshalers;

import org.gmdev.jargs.exception.JargsException;

import java.util.Iterator;

public interface ArgumentMarshaler {

    void set(Iterator<String> currentArgument) throws JargsException;
}
