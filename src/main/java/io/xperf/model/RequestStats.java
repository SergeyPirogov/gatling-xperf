package io.xperf.model;

public class RequestStats {

    private String requestName;
    private int indice;
    private String simulationName;
    private String scenario;
    protected String startDate;
    protected long count, successCount, errorCount;
    protected long min, max, stddev, p50, p90, p95, p99;
    protected double rps, avg;
    protected double duration;
    protected int maxUsers;
    private long start;
    private long end;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setSuccessCount(long successCount) {
        this.successCount = successCount;
    }

    public void setErrorCount(long errorCount) {
        this.errorCount = errorCount;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public void setStddev(long stddev) {
        this.stddev = stddev;
    }

    public void setP50(long p50) {
        this.p50 = p50;
    }

    public void setP90(long p90) {
        this.p90 = p90;
    }

    public void setP95(long p95) {
        this.p95 = p95;
    }

    public void setP99(long p99) {
        this.p99 = p99;
    }

    public void setRps(double rps) {
        this.rps = rps;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getRequestName() {
        return requestName;
    }

    public int getIndice() {
        return indice;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public String getScenario() {
        return scenario;
    }

    public String getStartDate() {
        return startDate;
    }

    public long getCount() {
        return count;
    }

    public long getSuccessCount() {
        return successCount;
    }

    public long getErrorCount() {
        return errorCount;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public long getStddev() {
        return stddev;
    }

    public long getP50() {
        return p50;
    }

    public long getP90() {
        return p90;
    }

    public long getP95() {
        return p95;
    }

    public long getP99() {
        return p99;
    }

    public double getRps() {
        return rps;
    }

    public double getAvg() {
        return avg;
    }

    public double getDuration() {
        return duration;
    }

    public int getMaxUsers() {
        return maxUsers;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getStart() {
        return start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getEnd() {
        return end;
    }
}
