package org.jasef.framework.configuration.impl;

import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.configuration.Configuration;

@Slf4j
public class SystemPropertiesConfiguration implements Configuration {

  @Override
  public String get(String key) {
    return System.getProperty(key);
  }

  /**
   * Stores a System property or updates an existing System property.
   * NOTE: this method will refuse to store or update properties without the prefix {@code "JASEF_"}.
   * @param key   key to store configuration value for
   * @param value value to store with provided key
   * @return the previous value of the System property or {@code null} if it didn't have one
   */
  @Override
  public String store(String key, String value) {
    if (!key.startsWith("JASEF_")) {
      log.error("JASeF system properties are required to have the prefix \"JASEF_\"");
      return null;
    }
    return System.setProperty(key, value);
  }

  /**
   * Deletes a property from system properties.
   * @param key key to delete
   * @return the previous string value of the system property, or {@code null} if there was no
   * property with that key.
   */
  @Override
  public String delete(String key) {
    return System.clearProperty(key);
  }

  /**
   * System property store cannot be deleted. Thus this method does nothing but return {@code true}.
   * @return always {@code true}
   */
  @Override
  public boolean deleteConfigStore() {
    // Since System property store cannot be deleted, this method will do nothing and always return
    // true
    return true;
  }
}
