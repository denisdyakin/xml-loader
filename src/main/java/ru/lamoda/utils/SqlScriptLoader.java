package ru.lamoda.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SqlScriptLoader {
    private static final Logger log = LoggerFactory.getLogger(SqlScriptLoader.class);
    private SqlScriptLoader(){}

    public static String loadScript(String file) {
        try {
            return IOUtils.toString(SqlScriptLoader.class.getClassLoader().getResourceAsStream(file));
        } catch (IOException ex) {
            log.error("load '{}' script error", file, ex);
            throw new RuntimeException(ex);
        }
    }
}
