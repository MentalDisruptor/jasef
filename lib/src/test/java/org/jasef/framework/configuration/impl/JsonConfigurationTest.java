package org.jasef.framework.configuration.impl;

import org.assertj.core.api.WithAssertions;
import org.jasef.framework.configuration.IConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

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
                .isEqualTo("testValue");
    }
}
