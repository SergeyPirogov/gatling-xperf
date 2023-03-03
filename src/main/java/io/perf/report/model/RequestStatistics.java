package io.perf.report.model;

public class RequestStatistics {

    protected String startDate;
    protected long count, successCount, errorCount;
    protected long min, max, stddev, p50, p90, p95, p99;
    protected double rps, avg;
    protected double duration;
    protected int maxUsers;
}
