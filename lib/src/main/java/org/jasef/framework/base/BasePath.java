package org.jasef.framework.base;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BasePath {

    public Path getBasePath() {
        return Paths.get(System.getProperty("user.dir"));
    }
}
