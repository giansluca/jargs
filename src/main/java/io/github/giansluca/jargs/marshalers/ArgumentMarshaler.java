package io.github.giansluca.jargs.marshalers;

import io.github.giansluca.jargs.exception.JargsException;

import java.util.Iterator;

public interface ArgumentMarshaler {

    void set(Iterator<String> currentArgument) throws JargsException;
}
