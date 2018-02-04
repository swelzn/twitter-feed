package com.zeromargin.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Utility reader class.
 */

public class ReaderUtil {

    public static String readFile(String name) throws URISyntaxException, IOException {
        Path path = Paths.get(ReaderUtil.class.getClassLoader().getResource(name).toURI());
        StringBuilder stringBuilder = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> stringBuilder.append(line).append("\n"));
        lines.close();
        return stringBuilder.toString();
    }

}
