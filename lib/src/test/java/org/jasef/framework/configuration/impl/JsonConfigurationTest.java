package org.jasef.framework.configuration.impl;

import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.WithAssertions;
import org.jasef.framework.base.BasePath;
import org.jasef.framework.configuration.Configuration;
import org.jasef.framework.configuration.exception.ConfigurationStorageException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@Slf4j
class JsonConfigurationTest implements WithAssertions {

  private static final String UNIT_TEST_KEY = "unitTestKey";
  private static final String UNIT_TEST_VALUE = "unitTestValue";

  private static final String FILENAME = "unitTest.json";
  private static final String INVALID_JSON = "invalid.json";

  Configuration configuration;

  @BeforeEach
  void setUp() throws IOException {
    //noinspection ConstantConditions
    File configFile = new File(
        this.getClass()
            .getClassLoader()
            .getResource("configuration/impl/config.json")
            .getFile()
    );
    configuration = new JsonConfiguration(configFile.getPath());
    BasePath basePath = new BasePath();
    File unitTestConfig = new File(basePath.getBasePath().toFile().getPath() + File.separator + FILENAME);
    if (!unitTestConfig.exists()) {
      Files.copy(
          configFile.toPath(),
          unitTestConfig.toPath()
      );
    }
  }

  @Test
  void testGet() {
    assertThat(configuration.get("testKey"))
        .withFailMessage("values are not equal")
        .isEqualTo("testValue");
  }

  @Test
  void testStoreAndDelete() throws IOException, ConfigurationStorageException {
    BasePath basePath = new BasePath();
    log.debug(basePath.getBasePath().toFile().getAbsolutePath() + File.separator + FILENAME);
    File testConfigFile = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + FILENAME);
    Configuration testConfig = new JsonConfiguration(testConfigFile.getPath());
    assertThat(testConfig.store(UNIT_TEST_KEY, UNIT_TEST_VALUE))
        .withFailMessage("storing entry unsuccessful")
        .isNull();
    assertThat(testConfig.store(UNIT_TEST_KEY, UNIT_TEST_VALUE + "2"))
        .withFailMessage("overwriting entry unsuccessful")
        .isEqualTo(UNIT_TEST_VALUE);
    assertThat(testConfig.delete("unitTestKey"))
        .withFailMessage("deleting entry unsuccessful")
        .isEqualTo(UNIT_TEST_VALUE + "2");
  }

  @Test
  void testInvalidConfigFile() {
    Configuration invalidConfiguration = new JsonConfiguration(INVALID_JSON);
    assertThat(invalidConfiguration.get("testKey")).isNull();
  }

  @Test
  void testDeleteConfigFile() throws IOException {
    BasePath basePath = new BasePath();
    File testConfigFile = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + FILENAME);
    boolean created = testConfigFile.exists();
    if (!created) {
      created = testConfigFile.createNewFile();
    }

    if (created) {
      Configuration testConfig = new JsonConfiguration(testConfigFile.getPath());
      assertThat(testConfig.deleteConfigStore()).isTrue();
    } else {
      fail("Test config file could not be created");
    }
  }

  @AfterEach
  void tearDown() throws IOException {
    BasePath basePath = new BasePath();
    File unitTestConfig = new File(basePath.getBasePath().toFile().getPath() + File.separator + FILENAME);
    if (unitTestConfig.exists()) {
      Files.delete(unitTestConfig.toPath());
    }
  }

  @AfterAll
  static void cleanup() {
    BasePath basePath = new BasePath();
    File unitTestJson = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + FILENAME
    );
    if (unitTestJson.exists()) {
      //noinspection ResultOfMethodCallIgnored
      unitTestJson.delete();
    }
    File invalidJson = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + INVALID_JSON
    );
    if (invalidJson.exists()) {
      //noinspection ResultOfMethodCallIgnored
      invalidJson.delete();
    }
  }

}
