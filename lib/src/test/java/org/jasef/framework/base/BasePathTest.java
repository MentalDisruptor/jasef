package org.jasef.framework.base;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

@Slf4j
class BasePathTest implements WithAssertions {

  @Test
  void getBasePathTest() {
    BasePath basePath = new BasePath();
    assertThat(basePath.getBasePath())
        .withFailMessage("Base path does not exist")
        .exists();
    assertThat(basePath.getBasePath())
        .withFailMessage("Base path is not a directory")
        .isDirectory();
    assertThat(basePath.getBasePath())
        .withFailMessage("Base path is not readable")
        .isReadable();
  }
}
