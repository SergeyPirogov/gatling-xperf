package io.xperf.diff;

public class Diff {

    private final String left;
    private final String right;

    public Diff(String left, String right) {
        this.left = left;
        this.right = right;
    }

    public String getRight() {
        return right;
    }

    public static Diff of(String left, String right) {
        return new Diff(left, right);
    }

    public static Diff of(long left, long right) {
        return new Diff(String.valueOf(left), String.valueOf(right));
    }

    private static long calculatePercentDiffPercent(long left, long right) {
        double res = ((double) (right - left) / right) * 100;
        return Math.round(res);
    }

    public String toString(boolean includePercent) {
        if (includePercent) {
            long percentDiffPercent = calculatePercentDiffPercent(Long.parseLong(left), Long.parseLong(right));
            return left + " -> " + right + " (" + percentDiffPercent + "%)";
        }
        if (left.equals(right)) {
            return left;
        }
        return left + " -> " + right;
    }

    @Override
    public String toString() {
        return toString(false);
    }
}
