package org.jasef.framework.configuration.impl;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
public class SystemEnvironmentConfigurationTest implements WithAssertions {

  private SystemEnvironmentConfiguration systemEnvironmentConfiguration;
  private static final String UNIT_TEST_KEY_1 = "unitTestKey";
  private static final String UNIT_TEST_VALUE_1 = "unitTestValue";
  private static final String UNIT_TEST_KEY_2 = "anotherTestKey";
  private static final String UNIT_TEST_VALUE_2 = "anotherTestValue";

  @BeforeEach
  void setUp() {
    systemEnvironmentConfiguration = new SystemEnvironmentConfiguration();
    systemEnvironmentConfiguration.store(UNIT_TEST_KEY_1, UNIT_TEST_VALUE_1);
  }

  @Test
  void testGet() {
    assertThat(systemEnvironmentConfiguration.get(UNIT_TEST_KEY_1)).isNotEmpty();
    assertThat(systemEnvironmentConfiguration.get(UNIT_TEST_KEY_1)).isEqualTo(UNIT_TEST_VALUE_1);
  }

  @Test
  void testStore() {
    assertThat(systemEnvironmentConfiguration.store(UNIT_TEST_KEY_2, UNIT_TEST_VALUE_2)).isBlank();
    assertThat(systemEnvironmentConfiguration.get(UNIT_TEST_KEY_2)).isEqualTo(UNIT_TEST_VALUE_2);
  }

  @Test
  void testReadValueDoesNotExistOnNewInstance() {
    assertThat(systemEnvironmentConfiguration.get(UNIT_TEST_KEY_2)).isNull();
  }

  @Test
  void testDelete() {
    assertThat(systemEnvironmentConfiguration.delete(UNIT_TEST_KEY_1)).isNotBlank();
    assertThat(systemEnvironmentConfiguration.get(UNIT_TEST_KEY_1)).isNull();
  }

  @AfterEach
  void tearDown() {
    systemEnvironmentConfiguration.delete("unitTestKey");
    systemEnvironmentConfiguration = null;
  }

}
