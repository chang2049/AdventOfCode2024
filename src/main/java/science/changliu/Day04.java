package science.changliu;

import science.changliu.utils.FileHelper;

public class Day04 {
    private final Character[][] input;
    private final Character[] word = {'X', 'M', 'A', 'S'};

    // Direction arrays for all 8 directions
    private final int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private final int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    public Day04() {
        FileHelper fileHelper = new FileHelper();
        this.input = fileHelper.loadContent(4).stream()
                .map(str -> str.chars()
                        .mapToObj(ch -> (char) ch)
                        .toArray(Character[]::new))
                .toArray(Character[][]::new);
    }

    public int solvePart1() {
        int count = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[0].length; y++) {
                if (input[x][y] == word[0]) {
                    for (int dir = 0; dir < 8; dir++) {
                        count += dfs(1, x + dx[dir], y + dy[dir], dir);
                    }
                }
            }
        }
        return count;
    }

    public int solvePart2() {
        int count = 0;
        for (int x = 1; x < input.length - 1; x++) {
            for (int y = 1; y < input[0].length - 1; y++) {
                if (isXMas(x, y)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isXMas(int x, int y) {
        // Center must be 'A'
        if (input[x][y] != 'A') {
            return false;
        }

        // Check diagonal 1 (top-left to bottom-right)
        boolean diag1Forward = input[x-1][y-1] == 'M' && input[x+1][y+1] == 'S';
        boolean diag1Backward = input[x-1][y-1] == 'S' && input[x+1][y+1] == 'M';

        // Check diagonal 2 (top-right to bottom-left)
        boolean diag2Forward = input[x-1][y+1] == 'M' && input[x+1][y-1] == 'S';
        boolean diag2Backward = input[x-1][y+1] == 'S' && input[x+1][y-1] == 'M';

        // Must have one valid MAS in each diagonal
        return (diag1Forward || diag1Backward) && (diag2Forward || diag2Backward);
    }

    private int dfs(int level, int x, int y, int direction) {
        // Check bounds
        if (x < 0 || y < 0 || x >= input.length || y >= input[0].length) {
            return 0;
        }

        // Check if current character matches expected character
        if (input[x][y] != word[level]) {
            return 0;
        }

        // If we've found a complete XMAS
        if (level == 3) {
            return 1;
        }

        // Continue in the same direction
        return dfs(level + 1, x + dx[direction], y + dy[direction], direction);
    }

    public static void main(String[] args) {
        Day04 solution = new Day04();
        System.out.println("Part 1 Solution: " + solution.solvePart1());
        System.out.println("Part 2 Solution: " + solution.solvePart2());
    }
}