package org.jasef.framework.configuration.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jasef.framework.base.BasePath;
import org.jasef.framework.configuration.IConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsonConfiguration implements IConfiguration {

    File file;
    Map<String, Object> jsonConfig = new HashMap<>();

    public JsonConfiguration(@NonNull String filename) {
        this.file = new BasePath().getBasePath().resolve(filename).toFile();
    }

    /**
     * Gets the value for the given key from JSON configuration
     * @param key the name of the property to be retrieved
     * @return
     */
    @Override
    public String get(String key) {
        if (jsonConfig.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                jsonConfig = mapper.readValue(file, Map.class);
            } catch (IOException e) {
                log.error("Could not parse JSON configuration");
                log.error("Caught {} with message \"{}\"", e.getClass(), e.getMessage(), e);
            }
        }

        if (jsonConfig.containsKey(key)) {
            return (String) jsonConfig.get(key);
        }
        return null;
    }

    /**
     * Stores a value with the given key in JSON configuration
     * @param key key to store configuration value for
     * @param value value to store with provided key
     * @return the value that was stored for given key, null if storing was unsuccessful
     */
    @Override
    public String store(String key, String value) {
        jsonConfig.put(key, value);
        if (saveToFile()) {
            return (String) jsonConfig.get(key);
        }
        return null;
    }

    /**
     * Deletes a given key from JSON configuration
     * @param key key to delete
     * @return value that was deleted, null if deletion was not successful
     */
    @Override
    public String delete(String key) {
        if (jsonConfig.containsKey(key)) {
            String value = (String) jsonConfig.get(key);
            jsonConfig.remove(key);
            if (saveToFile()) {
                return value;
            }
        }
        return null;
    }

    /**
     * Stores mapped configuration to the corresponding json file;
     * @return {@code true} if saving was successful, {@code false} otherwise
     */
    private boolean saveToFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(file, jsonConfig);
            return true;
        } catch (JsonGenerationException jsonGenerationException) {
            return false;
        } catch (IOException ioException) {
            log.error("Unable to write configuration file '{}'", file.getAbsolutePath());
            log.debug(
                    "Caught {} with message '{}'",
                    ioException.getClass(),
                    ioException.getMessage(),
                    ioException
            );
            return false;
        }
    }

    /**
     * Deletes the configuration file from disk entirely.
     * @return {@code true} if deleting was successful, {@code false} otherwise
     */
    public boolean deleteConfigStore() {
        // TODO check SonarLint issue below
        return file.delete();
    }
}
