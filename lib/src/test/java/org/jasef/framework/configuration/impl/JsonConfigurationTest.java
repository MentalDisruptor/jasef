package org.jasef.framework.configuration.impl;

import org.assertj.core.api.WithAssertions;
import org.jasef.framework.base.BasePath;
import org.jasef.framework.configuration.IConfiguration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class JsonConfigurationTest implements WithAssertions {

  IConfiguration configuration;

  @BeforeEach
  void setUp() {
    File configFile = new File(
        JsonConfigurationTest.class
            .getClassLoader()
            .getResource("configuration/impl/config.json")
            .getFile()
    );
    configuration = new JsonConfiguration(configFile.getPath());
  }

  @Test
  void testGet() {
    assertThat(configuration.get("testKey"))
        .withFailMessage("values are not equal")
        .isEqualTo("testValue");
  }

  @Test
  void testStoreAndDelete() throws IOException {
    BasePath basePath = new BasePath();
    File testConfigFile = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + "unitTest.json");
    testConfigFile.createNewFile();
    IConfiguration testConfig = new JsonConfiguration(testConfigFile.getPath());
    assertThat(testConfig.store("unitTestKey", "unitTestValue"))
        .withFailMessage("storing entry unsuccessful")
        .isEqualTo("unitTestValue");
    assertThat(testConfig.delete("unitTestKey"))
        .withFailMessage("deleting entry unsuccessful")
        .isEqualTo("unitTestValue");
  }

  @Test
  void testInvalidConfigFile() {
    IConfiguration invalidConfiguration = new JsonConfiguration("invalid.json");
    assertThat(invalidConfiguration.get("testKey")).isNull();
  }

  @Test
  void testDeleteConfigFile() throws IOException {
    BasePath basePath = new BasePath();
    File testConfigFile = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + "unitTest.json");
    boolean created = testConfigFile.exists();
    if (!created) {
      created = testConfigFile.createNewFile();
    }

    if (created) {
      IConfiguration testConfig = new JsonConfiguration(testConfigFile.getPath());
      assertThat(testConfig.deleteConfigStore()).isTrue();
    } else {
      fail("Test config file could not be created");
    }
  }

  @AfterAll
  static void cleanup() {
    BasePath basePath = new BasePath();
    File unitTestJson = new File(
        basePath.getBasePath().toFile().getPath() + File.separator + "unitTest.json");
    if (unitTestJson.exists()) {
      unitTestJson.delete();
    }
  }

}
