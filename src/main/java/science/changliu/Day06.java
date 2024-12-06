package science.changliu;

import science.changliu.utils.FileHelper;

import java.util.*;

public class Day06 {
    private final char[][] map;
    private int[] startPosition;
    private final int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // up, right, down, left

    public Day06() {
        FileHelper fileHelper = new FileHelper();
        List<String> input = fileHelper.loadContent(6);
        map = new char[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            map[i] = input.get(i).toCharArray();
            int caretIndex = input.get(i).indexOf('^');
            if (caretIndex != -1) {
                startPosition = new int[]{i, caretIndex};
            }
        }
    }

    private Set<String> simulatePathPart1(char[][] currentMap, int direction, int[] pos) {
        Set<String> visited = new HashSet<>();
        int[] currentPos = pos.clone();
        int currentDirection = direction;

        while (true) {
            // Add current position to visited set
            visited.add(currentPos[0] + "," + currentPos[1]);

            // Calculate next position
            int nextRow = currentPos[0] + directions[currentDirection][0];
            int nextCol = currentPos[1] + directions[currentDirection][1];

            // Check if out of bounds
            if (nextRow < 0 || nextRow >= currentMap.length || nextCol < 0 || nextCol >= currentMap[0].length) {
                return visited;
            }

            // Check if there's an obstacle
            if (currentMap[nextRow][nextCol] == '#') {
                // Turn right
                currentDirection = (currentDirection + 1) % 4;
            } else {
                // Move forward
                currentPos = new int[]{nextRow, nextCol};
            }
        }
    }

    private List<int[]> simulatePathPart2(char[][] testMap, int direction, int[] pos) {
        List<int[]> path = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        int[] currentPos = pos.clone();
        int currentDirection = direction;

        while (true) {
            // Add current position to path
            path.add(currentPos.clone());

            // Create state string for loop detection
            String state = currentPos[0] + "," + currentPos[1] + "," + currentDirection;
            if (visited.contains(state)) {
                return path; // Loop detected
            }
            visited.add(state);

            // Calculate next position
            int nextRow = currentPos[0] + directions[currentDirection][0];
            int nextCol = currentPos[1] + directions[currentDirection][1];

            // Check if out of bounds
            if (nextRow < 0 || nextRow >= testMap.length || nextCol < 0 || nextCol >= testMap[0].length) {
                return null; // Path leads out of bounds
            }

            // Check if there's an obstacle
            if (testMap[nextRow][nextCol] == '#') {
                // Turn right
                currentDirection = (currentDirection + 1) % 4;
            } else {
                // Move forward
                currentPos = new int[]{nextRow, nextCol};
            }
        }
    }

    public int solvePart1() {
        Set<String> visitedPositions = simulatePathPart1(map, 0, startPosition);
        return visitedPositions.size();
    }

    public int solvePart2() {
        int count = 0;

        // Try placing obstacle at each empty position
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                // Skip if position is already occupied or is start position
                if (map[i][j] != '.' || (i == startPosition[0] && j == startPosition[1])) {
                    continue;
                }

                // Create a test map with new obstacle
                char[][] testMap = new char[map.length][];
                for (int k = 0; k < map.length; k++) {
                    testMap[k] = map[k].clone();
                }
                testMap[i][j] = '#';

                // Simulate path with new obstacle
                List<int[]> path = simulatePathPart2(testMap, 0, startPosition);

                // If path forms a loop, increment count
                if (path != null) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Day06 solution = new Day06();
        System.out.println("Part 1 Solution: " + solution.solvePart1());
        System.out.println("Part 2 Solution: " + solution.solvePart2());
    }
}