package org.jasef.framework.configuration;

import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.base.throwables.DriverNotCreatedException;

@Slf4j
public class SystemPropertyHandler {

  private SystemPropertyHandler() {
    // private constructor to hide the implicit public one
  }

  /**
   * Gets the value of a system property
   *
   * @param property desired system property
   * @return the system property value, or {@code null} on failure or when the key not exists
   */
  public static String get(String property) {
    try {
      return System.getProperty(property);
    } catch (RuntimeException runtimeException) {
      // warn about encountered runtime exceptions, but don't act on them
      log.warn(
          "Cannot to read system property \"{}\".",
          property
      );
      // provide some more information on debug loglevel.
      log.debug(runtimeException.getMessage(), runtimeException);
    }
    return null;
  }
}
