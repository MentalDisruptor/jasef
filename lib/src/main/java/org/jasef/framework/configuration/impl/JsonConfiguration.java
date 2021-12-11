package org.jasef.framework.configuration.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.configuration.ConfigFile;
import org.jasef.framework.configuration.Configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonConfiguration implements Configuration {

  private final ConfigFile configFile;
  Map<String, Object> jsonConfig = new HashMap<>();

  /**
   * Creates a new JSON-based configuration
   *
   * @param filename filename configuration is to be read from and stored to
   */
  public JsonConfiguration(@NonNull String filename) {
    this.configFile = new ConfigFile(filename);
  }

  private Map<String, Object> initConfig() {
    if (!this.configFile.exists()) {
      this.configFile.createFile();
      return Collections.emptyMap();
    }

    ObjectMapper mapper = new ObjectMapper();
    TypeReference<HashMap<String, Object>> typeReference
        = new TypeReference<>() {
    };

    try {
      return mapper.readValue(this.configFile.getFile(), typeReference);
    } catch (IOException e) {
      log.error("Could not parse JSON configuration");
      log.error("Caught {} with message \"{}\"", e.getClass(), e.getMessage(), e);
      return Collections.emptyMap();
    }
  }

  /**
   * Gets the value for the given key from JSON configuration
   *
   * @param key the name of the property to be retrieved
   * @return {@link String} value for the given key or {@code null} if there is no mapping for the
   * given key or if config file cannot be read.
   */
  @Override
  public String get(String key) {
    if (jsonConfig.isEmpty()) {
      jsonConfig = initConfig();
    }
    if (jsonConfig != null) {
      return (String) jsonConfig.get(key);
    }
    return null;
  }

  /**
   * Stores a value with the given key in JSON configuration
   *
   * @param key   key to store configuration value for
   * @param value value to store with given key
   * @return the previous {@link String} value that was stored for given key, or null if there was
   * no mapping for the key. (A {@code null} return can also indicate that the map previously
   * associated {@code null} with the key.)
   */
  @Override
  public String store(String key, String value) {
    if (!configFile.exists()) {
      configFile.createFile();
      return null;
    }

    if (jsonConfig.isEmpty()) {
      jsonConfig = initConfig();
    }

    if (saveToFile()) {
      return (String) jsonConfig.put(key, value);
    }
    return null;
  }

  /**
   * Deletes a given key from JSON configuration
   *
   * @param key key to delete
   * @return {@link String} value that was associated with the key deleted, null if there was no
   * mapping for the key
   */
  @Override
  public String delete(String key) {
    if (jsonConfig.isEmpty()) {
      jsonConfig = initConfig();
    }
    if ((jsonConfig != null) && (jsonConfig.containsKey(key))) {
      String value = (String) jsonConfig.remove(key);
      if (saveToFile()) {
        return value;
      }
    }
    return null;
  }

  /**
   * Stores mapped configuration to the corresponding json file;
   *
   * @return {@code true} if saving was successful, {@code false} otherwise
   */
  private boolean saveToFile() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      mapper.writeValue(configFile.getFile(), jsonConfig);
      return true;
    } catch (JsonGenerationException jsonGenerationException) {
      return false;
    } catch (IOException ioException) {
      log.error("Unable to write configuration file '{}'", configFile.getFile().getAbsolutePath());
      log.debug(
          "Caught {} with message '{}'",
          ioException.getClass(),
          ioException.getMessage(),
          ioException
      );
      return false;
    }
  }

  /**
   * Deletes the configuration file from disk entirely.
   *
   * @return {@code true} if deleting was successful, {@code false} otherwise
   */
  public boolean deleteConfigStore() {
    // TODO check SonarLint issue below
    return configFile.getFile().delete();
  }
}
