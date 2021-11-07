package org.jasef.framework.base;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;

@Slf4j
public class BasePath {

    private String getPathToClass() {
        return new File(
                BasePath.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath())
                .getName();
    }

    private boolean isRunningFromJar() {
        return getPathToClass().contains(".jar");
    }

    private Path getCurrentProjectDirectory() {
        return new File(".").getAbsoluteFile().toPath().getParent();
    }

    private Path getCurrentJarDirectory() {
        return new File(
                BasePath.class
                        .getProtectionDomain()
                        .getCodeSource()
                        .getLocation()
                        .getPath()
        ).toPath();
    }

    /**
     * Gets the project base path
     * @return
     */
    public Path getBasePath() {
        if (isRunningFromJar()) {
            log.debug("Running from JAR.");
            return getCurrentJarDirectory();
        } else {
            log.debug("Not running from JAR.");
            return getCurrentProjectDirectory();
        }
    }
}
