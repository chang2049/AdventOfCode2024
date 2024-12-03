package science.changliu.utils;

import java.util.List;
import java.util.stream.Collectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {
    private static final String FILE_PATH = "day";
    private static final String FILE_EXTENSION = ".txt";

    public List<String> loadContent(int dayCount) {
        return loadContent(dayCount, null);
    }

    public List<String> loadContent(int dayCount, Integer partCount) {
        String dayCountStr = String.format("%02d", dayCount);
        String fileName = FILE_PATH + dayCountStr +
                (partCount != null ? "-" + partCount : "") +
                FILE_EXTENSION;
        return readFile(fileName);
    }

    private List<String> readFile(String fileName) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            if (is == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }

            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    public static List<Integer> convertStringToIntList(List<String> list) {
        return list.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public static List<Long> convertStringToLongList(List<String> list) {
        return list.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public List<String> loadContentAsSingleString(int dayCount) {
        return loadContent(dayCount).stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public String[] loadContentAsArray(int dayCount) {
        return loadContent(dayCount).toArray(new String[0]);
    }

    public List<List<String>> loadContentAsMatrix(int dayCount) {
        return loadContent(dayCount).stream()
                .map(line -> List.of(line.split("")))
                .collect(Collectors.toList());
    }

    public int[][] loadContentAsIntMatrix(int dayCount) {
        List<String> lines = loadContent(dayCount);
        int[][] matrix = new int[lines.size()][];

        for (int i = 0; i < lines.size(); i++) {
            matrix[i] = lines.get(i).chars()
                    .map(Character::getNumericValue)
                    .toArray();
        }
        return matrix;
    }

    public List<String> loadContentSplitByEmptyLine(int dayCount) {
        StringBuilder content = new StringBuilder();
        for (String line : loadContent(dayCount)) {
            content.append(line).append("\n");
        }

        return List.of(content.toString().split("\n\n"));
    }
}