package org.jasef.framework.configuration.impl;

import org.assertj.core.api.WithAssertions;
import org.jasef.framework.configuration.Configuration;
import org.jasef.framework.configuration.exception.ConfigurationStorageException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SystemPropertiesConfigurationTest implements WithAssertions {

  private static Configuration configuration;
  private static final String TEST_PROPERTY_KEY = "JASEF_unitTest";
  private static final String TEST_PROPERTY_VALUE = "testValue";
  private static final String STORE_TEST_PROPERTY_KEY = "JASEF_testStoreKey";
  private static final String STORE_PREFIXLESS_KEY = "testKey";

  @BeforeAll
  static void setUp() {
    System.setProperty(TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE);
    configuration = new SystemPropertiesConfiguration();
  }

  @Test
  void testGet() {
    assertThat(configuration.get(TEST_PROPERTY_KEY))
        .withFailMessage("Property value is not equal")
        .isEqualTo(TEST_PROPERTY_VALUE);
  }

  @Test
  void testStore() throws ConfigurationStorageException {
    assertThat(configuration.store(STORE_TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE))
        .withFailMessage("Store System property failed")
        .isNull();
    assertThat(configuration.store(STORE_PREFIXLESS_KEY, TEST_PROPERTY_VALUE))
        .withFailMessage("Store System property without required prefix worked")
        .isNull();
    System.clearProperty(STORE_PREFIXLESS_KEY);
  }

  @Test
  void testDelete() {
    assertThat(configuration.delete(STORE_TEST_PROPERTY_KEY))
        .withFailMessage("Delete System property failed")
        .isEqualTo(TEST_PROPERTY_VALUE);

  }

  @Test
  void testDeleteConfigStore() {
    assertThat(configuration.deleteConfigStore())
        .withFailMessage("Unexpected config store deletion behavior")
        .isTrue();
  }

  @AfterAll
  static void tearDown() {
    System.clearProperty(TEST_PROPERTY_KEY);
  }

}
