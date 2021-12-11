package org.jasef.framework.configuration.impl;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.configuration.Configuration;
import org.jasef.framework.configuration.exception.ConfigurationStorageException;

@Slf4j
public class SystemEnvironmentConfiguration implements Configuration {

  private final Map<String, String> systemEnvironment = new HashMap<>();

  /**
   * System environment configuration provider.
   * This configuration provider creates a class instance copy of System.getenv() and operates on
   * that copied map. Any changes are lost once the instance of this provider is abandoned!
   */
  public SystemEnvironmentConfiguration() {
    systemEnvironment.putAll(System.getenv());
    log.info("Copied system environment to class instance.");
  }

  /**
   * Retrieves a property from copied system environment
   * @param key the name of the property to be retrieved
   * @return
   */
  @Override
  public String get(String key) {
    return systemEnvironment.get(key);
  }

  /**
   * Stub method, storing to System environment is not supported.
   * @param key will be omitted
   * @param value will be omitted
   * @return always {@code null} since storing to System environment is not supported.
   */
  @Override
  public String store(String key, String value) {
    log.warn(
        """
        Storing values to System environment is not a permanent storage option.
        To store this configuration value permanently, use a file-based configuration store.
        stored key: '{}'; stored value: '{}'
        """, key, value);
    return systemEnvironment.put(key, value);
  }

  @Override
  public String delete(String key) {
    return systemEnvironment.remove(key);
  }

  @Override
  public boolean deleteConfigStore() {
    this.systemEnvironment.clear();
    return systemEnvironment.isEmpty();
  }

}
