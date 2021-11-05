package org.jasef.framework.base.throwables;

public class DriverNotCreatedException extends Exception {

    public DriverNotCreatedException(String message) {
        super(message);
    }

    public DriverNotCreatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
