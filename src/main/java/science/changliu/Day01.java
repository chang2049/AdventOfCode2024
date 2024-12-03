package science.changliu;

import science.changliu.utils.FileHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day01 {
    private final FileHelper fileHelper;
    private List<Integer> leftList;
    private List<Integer> rightList;

    public Day01() {
        this.fileHelper = new FileHelper();
        loadLists();
    }

    private void loadLists() {
        List<String> lines = fileHelper.loadContent(1);
        leftList = new ArrayList<>();
        rightList = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue;
            }
            String[] parts = line.trim().split("\\s+");
            if (parts.length == 2) {
                leftList.add(Integer.parseInt(parts[0]));
                rightList.add(Integer.parseInt(parts[1]));
            }
        }
    }

    public long solvePart1() {
        List<Integer> sortedLeft = new ArrayList<>(leftList);
        List<Integer> sortedRight = new ArrayList<>(rightList);

        Collections.sort(sortedLeft);
        Collections.sort(sortedRight);

        long totalDistance = 0;
        for (int i = 0; i < sortedLeft.size(); i++) {
            totalDistance += Math.abs(sortedLeft.get(i) - sortedRight.get(i));
        }

        return totalDistance;
    }

    public long solvePart2() {
        Map<Integer, Long> rightFrequencies = rightList.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        return leftList.stream()
                .mapToLong(num -> num * rightFrequencies.getOrDefault(num, 0L))
                .sum();
    }

    public static void main(String[] args) {
        Day01 solution = new Day01();
        System.out.println("Part 1 Solution: " + solution.solvePart1());
        System.out.println("Part 2 Solution: " + solution.solvePart2());
    }
}
