package com.ilya.elasticsearch.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Util {
    private static final Logger LOG = LoggerFactory.getLogger(Util.class);

    public static String loadAsString(final String path) {
        try {
            String content;
            ClassPathResource resource  = new ClassPathResource(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            content = reader.lines().collect(Collectors.joining("\n"));
            return content;
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }
}
