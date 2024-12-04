package science.changliu;

import science.changliu.utils.FileHelper;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {
    private final String input;

    public Day03() {
        FileHelper fileHelper = new FileHelper();
        this.input = String.join("", fileHelper.loadContent(3));
    }

    public int solvePart1() {
        // Pattern to match valid mul instructions: mul(X,Y) where X and Y are 1-3 digits
        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher matcher = pattern.matcher(input);

        int sum = 0;
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            sum += x * y;
        }

        return sum;
    }

    public long solvePart2() {
        Pattern mulPattern = Pattern.compile("\\bmul\\((\\d{1,3}),(\\d{1,3})\\)");
        Pattern doPattern = Pattern.compile("\\bdo\\(\\)");
        Pattern dontPattern = Pattern.compile("\\bdon't\\(\\)");

        long sum = 0;
        boolean enabled = true;  // mul instructions are enabled at the start

        int pos = 0;
        while (pos < input.length()) {
            Matcher doMatcher = doPattern.matcher(input).region(pos, input.length());
            Matcher dontMatcher = dontPattern.matcher(input).region(pos, input.length());
            Matcher mulMatcher = mulPattern.matcher(input).region(pos, input.length());

            int nextPos = input.length();
            boolean doFound = doMatcher.find();
            boolean dontFound = dontMatcher.find();
            boolean mulFound = mulMatcher.find();

            // Find the earliest position of any matching pattern
            if (doFound) {
                nextPos = Math.min(nextPos, doMatcher.start());
            }
            if (dontFound) {
                nextPos = Math.min(nextPos, dontMatcher.start());
            }
            if (mulFound) {
                nextPos = Math.min(nextPos, mulMatcher.start());
            }

            if (nextPos == input.length()) {
                break;  // No more instructions found
            }

            // Process the instruction at nextPos
            if (doFound && nextPos == doMatcher.start()) {
                enabled = true;
                pos = doMatcher.end();
            } else if (dontFound && nextPos == dontMatcher.start()) {
                enabled = false;
                pos = dontMatcher.end();
            } else if (mulFound && nextPos == mulMatcher.start() && enabled) {
                long x = Long.parseLong(mulMatcher.group(1));
                long y = Long.parseLong(mulMatcher.group(2));
                sum += x * y;
                pos = mulMatcher.end();
            } else {
                pos = nextPos + 1;  // Skip to next character if no valid instruction found
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        Day03 solution = new Day03();
        System.out.println("Part 1 Solution: " + solution.solvePart1());
        System.out.println("Part 2 Solution: " + solution.solvePart2());
    }
}