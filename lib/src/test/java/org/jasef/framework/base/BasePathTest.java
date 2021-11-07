package org.jasef.framework.base;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

class BasePathTest implements WithAssertions {

    @Test
    void getBasePathTest() {
        BasePath basePath = new BasePath();
        assertThat(basePath.getBasePath())
                .withFailMessage("Base path is not a directory")
                .isDirectory();
        assertThat(basePath.getBasePath())
                .withFailMessage("Base path is not readable")
                .isReadable();
        assertThat(basePath.getBasePath())
                .withFailMessage("Base path does not have expected value")
                .isEqualTo(Paths.get(System.getProperty("user.dir")));
    }
}
