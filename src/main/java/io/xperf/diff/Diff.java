package io.xperf.diff;

public class Diff {

    private final String left;
    private final String right;
    private String requestName;
    private String valueName;

    public Diff(String requestName, String valueName, String left, String right) {
        this.requestName = requestName;
        this.valueName = valueName;
        this.left = left;
        this.right = right;
    }

    public static Diff of(String requestName, String valueName, String left, String right) {
        return new Diff(requestName, valueName, left, right);
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public String getRequestName() {
        return requestName;
    }

    public String getValueName() {
        return valueName;
    }
}
