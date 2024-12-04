package science.changliu;

import science.changliu.utils.FileHelper;

import java.util.Arrays;

public class Day02 {
    private final FileHelper fileHelper;
    private final int[][] matrix;

    public Day02() {
        this.fileHelper = new FileHelper();
        this.matrix = fileHelper.loadContentAsIntMatrix(2);
    }

    private boolean isReportSafe(int[] report) {
        for (int col = 1; col < report.length - 1; col++) {
            int previous = report[col - 1];
            int current = report[col];
            int next = report[col + 1];
            if ((next - current) * (current - previous) < 0 || Math.abs(next - current) < 1 || Math.abs(next - current) > 3 || Math.abs(current - previous) < 1 || Math.abs(current - previous) > 3) {
                break;
            }
            if (col == report.length - 2) {
                return true;
            }
        }
        return false;
    }

    private boolean canBeMadeSafe(int[] report) {
        // Try removing each number once
        for (int i = 0; i < report.length; i++) {
            int[] modifiedReport = new int[report.length - 1];
            // Copy all elements except the one at index i
            System.arraycopy(report, 0, modifiedReport, 0, i);
            System.arraycopy(report, i + 1, modifiedReport, i, report.length - i - 1);

            if (isReportSafe(modifiedReport)) {
                return true;
            }
        }
        return false;
    }

    public long solvePart1() {
        return Arrays.stream(matrix).parallel().filter(this::isReportSafe).count();
    }

    public long solvePart2() {
        return Arrays.stream(matrix).parallel().filter(report -> isReportSafe(report) || canBeMadeSafe(report)).count();
    }

    public static void main(String[] args) {
        Day02 solution = new Day02();
        System.out.println("Part 1 Solution: " + solution.solvePart1());
        System.out.println("Part 2 Solution: " + solution.solvePart2());
    }

}
