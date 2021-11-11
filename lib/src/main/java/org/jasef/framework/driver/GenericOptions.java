package org.jasef.framework.driver;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;

import java.lang.reflect.InvocationTargetException;

@Slf4j
public class GenericOptions<T> {

  Class<T> typeArgumentClass;
  T browserType = null;

  public GenericOptions(Class<T> typeArgumentClass) {
    this.typeArgumentClass = typeArgumentClass;
  }

  /**
   * Returns a new {@link Capabilities} object based on type parameter passed to this class. If
   * creating a new {@link Capabilities} object fails for any reason, {@code null} will be
   * returned.
   *
   * @return browser-specific {@link Capabilities} object
   */
  public T create() {
    try {
      browserType = typeArgumentClass.getDeclaredConstructor().newInstance();
    } catch (InvocationTargetException invocationTargetException) {
      log.error("While creating a new instance of {}, the underlying constructor threw an error.",
          typeArgumentClass.getName());
      log.error("Error message: {}", invocationTargetException.getMessage());
    } catch (InstantiationException instantiationException) {
      log.error("Could not create an instance of abstract class {}.", typeArgumentClass.getName());
    } catch (IllegalAccessException illegalAccessException) {
      log.error("The constructor required to create a new instance of {} is inaccessible.",
          typeArgumentClass.getName());
    } catch (NoSuchMethodException noSuchMethodException) {
      log.error("Could not find a matching constructor for {}.", typeArgumentClass.getName());
    }
    return browserType;
  }
}
