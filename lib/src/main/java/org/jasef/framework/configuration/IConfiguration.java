package org.jasef.framework.configuration;

public interface IConfiguration {

  /**
   * Reads the configuration property for the given key from a configuration store.
   *
   * @param key the name of the property to be retrieved
   * @return the string value for the given key
   */
  public String get(String key);

  /**
   * Stores a configuration value for the given key in configuration.
   *
   * @param key   key to store configuration value for
   * @param value value to store with provided key
   * @return the configuration value stored under given key, {@code null} if storage was not
   * successful
   */
  public String store(String key, String value);

  /**
   * Deletes a configuration key from storage.
   *
   * @param key key to delete
   * @return value of key that was deleted, null if given key was not found or could not be deleted.
   */
  public String delete(String key);

  /**
   * Deletes an entire configuration store.
   *
   * @return {@code true} if deletion was successful, {@code false} otherwise
   */
  public boolean deleteConfigStore();
}
