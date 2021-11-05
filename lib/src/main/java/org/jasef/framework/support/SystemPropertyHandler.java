package org.jasef.framework.support;

import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.base.throwables.DriverNotCreatedException;

@Slf4j
public class SystemPropertyHandler {

    /**
     * Gets the value of a system property
     * @param property desired system property
     * @return the system property value, {@code null} on failure
     */
    public static String get(String property) {
        try {
            return System.getProperty(property);
        } catch (RuntimeException runtimeException) {
            // warn about encountered runtime exceptions, but don't act on them
            log.warn(
                    "Failed getting System property value due to {}, will return null value.",
                    runtimeException.getClass().getName()
            );
        }
        return null;
    }
}
