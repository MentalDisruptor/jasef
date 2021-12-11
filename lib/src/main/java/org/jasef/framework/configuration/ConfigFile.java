package org.jasef.framework.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.base.BasePath;

@Slf4j
public class ConfigFile {
  File file;

  public ConfigFile(String filename) {
    this.file = new BasePath().getBasePath().resolve(filename).toFile();
  }

  public File getFile() {
    return this.file;
  }

  public File createFile() {
    if (!exists()) {
      boolean created;
      try {
        created = this.file.createNewFile();
      } catch (IOException e) {
        return null;
      }
      if (created) {

        try (OutputStream stream = new FileOutputStream(file)){
          stream.write("{}".getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException fileNotFoundException) {
          // ignore as file was just created, but do a debug log
          log.debug("JSON config file not found!", fileNotFoundException);
          return null;
        } catch (IOException ioException) {
          log.error("Error writing JSON config file.", ioException);
          return null;
        }
        return file;
      }
    }
    return null;
  }

  public boolean exists() {
    return this.file.exists();
  }
}
