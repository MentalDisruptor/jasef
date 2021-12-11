package org.jasef.framework.configuration.impl;

import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.configuration.Configuration;

@Slf4j
public class SystemPropertiesConfiguration implements Configuration {

  /**
   * System environment configuration provider.
   * This provider does not support storage, yet.
   */
  public SystemPropertiesConfiguration() {
    // TODO: make a runtime copy to support temporary storage
  }

  private boolean isFrameworkProperty(String key) {
    boolean isFrameworkProperty = key.startsWith("JASEF_");
    if(!isFrameworkProperty) {
      log.error("JASeF system properties are required to have the prefix \"JASEF_\"");
    }
    return isFrameworkProperty;
  }

  /**
   * Gets the System property with the given key
   * @param key the name of the property to be retrieved
   * @return
   * the string value of the system property, or null if there is no property with that key.
   */
  @Override
  public String get(String key) {
    return System.getProperty(key);
  }

  /**
   * Stores a System property or updates an existing System property.
   * NOTE: this method will refuse to store or update properties without the prefix {@code "JASEF_"}.
   * @param key   key to store configuration value for
   * @param value value to store with provided key
   * @return the previous value of the System property, {@code null} if it didn't have one or if
   * the property to be stored was not a framework property
   */
  @Override
  public String store(String key, String value) {
    if (isFrameworkProperty(key)) {
      return System.setProperty(key, value);
    }
    return null;
  }

  /**
   * Deletes a property from system properties.
   * @param key key to delete
   * @return the previous string value of the system property, or {@code null} if there was no
   * property with that key or key was not a framework property.
   */
  @Override
  public String delete(String key) {
    if(isFrameworkProperty(key)) {
      return System.clearProperty(key);
    }
    return null;
  }

  /**
   * System property store cannot be deleted. Thus this method does nothing but return {@code true}.
   * @return always {@code true}
   */
  @Override
  public boolean deleteConfigStore() {
    // Since System property store cannot be deleted, this method will do nothing and always return
    // true
    log.info("This is a stub method: System property store cannot be deleted.");
    return true;
  }
}
