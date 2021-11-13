package org.jasef.framework.configuration;

public interface Configuration {

  /**
   * Reads the configuration property for the given key from a configuration store.
   *
   * @param key the name of the property to be retrieved
   * @return the string value for the given key
   */
  String get(String key);

  /**
   * Stores a configuration value for the given key in configuration.
   *
   * @param key   key to store configuration value for
   * @param value value to store with provided key
   * @return the previous value mapped with the given key, or {@code null} if there was no mapping.
   */
  String store(String key, String value);

  /**
   * Deletes a configuration key from storage.
   *
   * @param key key to delete
   * @return value of key that was deleted, null if given key was not found or could not be deleted.
   */
  String delete(String key);

  /**
   * Deletes an entire configuration store.
   *
   * @return {@code true} if deletion was successful, {@code false} otherwise
   */
  boolean deleteConfigStore();
}
