package org.jasef.framework.configuration.exception;

import java.io.File;

public class ConfigFileCreateException extends
    Exception {

  private final File file;

  public ConfigFileCreateException(String message, File file) {
    super(message);
    this.file = file;
  }

  public ConfigFileCreateException(String message, File file, Throwable cause) {
    super(message, cause);
    this.file = file;
  }

  public File getFile() {
    return file;
  }

}
