package science.changliu;

import science.changliu.utils.FileHelper;

import java.util.*;
import java.util.stream.Collectors;

public class Day05 {
    private final Map<Integer, List<Integer>> rules;
    private final List<List<Integer>> updates;

    private boolean isUpdateOrderCorrect(List<Integer> update) {
        for(int i = 0; i<update.size()-1; i++){
            int current = update.get(i);
            int next = update.get(i+1);
            if(rules.getOrDefault(next, Collections.emptyList()).contains(current)){
                return false;
            }
        }
        return true;
    }

    private List<Integer> topologicalSort(List<Integer> update) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (int page : update) {
            graph.put(page, new HashSet<>());
            inDegree.put(page, 0);
        }

        for (int page : update) {
            if (rules.containsKey(page)) {
                for (int after : rules.get(page)) {
                    if (update.contains(after)) {
                        graph.get(page).add(after);
                        inDegree.merge(after, 1, Integer::sum);
                    }
                }
            }
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a); // Higher numbers first
        for (int page : update) {
            if (inDegree.get(page) == 0) {
                queue.offer(page);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int page = queue.poll();
            result.add(page);

            for (int next : graph.get(page)) {
                inDegree.put(next, inDegree.get(next) - 1);
                if (inDegree.get(next) == 0) {
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    public Day05() {
        FileHelper fileHelper = new FileHelper();
        List<String> input = fileHelper.loadContentSplitByEmptyLine(5);

        rules = Arrays.stream(input.getFirst().split("\\r?\\n"))
                .map(rule -> rule.split("\\|"))  // Split by | character (needs escape)
                .collect(Collectors.groupingBy(
                        parts -> Integer.parseInt(parts[0]),  // Key: first number
                        Collectors.mapping(
                                parts -> Integer.parseInt(parts[1]),  // Value: second number
                                Collectors.toList()
                        )
                ));

        updates = Arrays.stream(input.get(1).split("\\r?\\n")).map(line-> Arrays.stream(line.split(",")).map(Integer::parseInt).toList()).toList();
    }

    public int solvePart1() {
        return updates.stream().filter(this::isUpdateOrderCorrect)
                .mapToInt(update -> update.get(update.size()/2)).sum();
    }

    public int solvePart2() {
        return updates.stream()
                .filter(update -> !isUpdateOrderCorrect(update))
                .map(this::topologicalSort)
                .mapToInt(update -> update.get(update.size()/2))
                .sum();
    }

    public static void main(String[] args) {
        Day05 solution = new Day05();
        System.out.println("Part 1 Solution: " + solution.solvePart1());
        System.out.println("Part 2 Solution: " + solution.solvePart2());
    }
}