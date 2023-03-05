package io.perf.report.model;

import io.perf.report.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationRequest {
    public static final long MAX_BOXPOINT = 50000;

    protected static final AtomicInteger statCounter = new AtomicInteger();
    protected final String request;
    protected final String requestId;
    protected final int indice;
    protected final List<Double> durations;
    protected String simulation;
    protected String scenario;
    protected long start;
    protected long end;
    protected long count;
    protected long errorCount;
    private Apdex apdex;

    //protected String startDate;

    //protected long count, successCount, errorCount;
//    protected long min, max, stddev, p50, p90, p95, p99;
//    protected double rps, avg;
//    protected double duration;
//    protected int maxUsers;

    public SimulationRequest(String simulation, String scenario, String request, long start) {
        this.simulation = simulation;
        this.scenario = scenario;
        this.request = request;
        requestId = Utils.getIdentifier(request);
        this.start = start;
        durations = new ArrayList<>();
        indice = statCounter.incrementAndGet();
        this.apdex = new Apdex();
    }

//    public static List<String> header() {
//        return List.of(("simulation,scenario,maxUsers,request,start,startDate,duration,end,count,successCount," +
//                "errorCount,min,p50,p90,p95,p99,max,avg,stddev,rps,apdex,rating")
//                .split(","));
//    }

    public void add(long start, long end, boolean success) {
        count += 1;
        if (this.start == 0) {
            this.start = start;
        }
        this.start = Math.min(this.start, start);
        this.end = Math.max(this.end, end);
        if (!success) {
            errorCount += 1;
        }
        long duration = end - start;
        durations.add((double) duration);
        apdex.addMs(duration);
    }

//    public void computeStat(int maxUsers) {
//        computeStat((end - start) / 1000.0, maxUsers);
//    }

//    public void computeStat(double duration, int maxUsers) {
//        double[] times = getDurationAsArray();
//        min = (long) StatUtils.min(times);
//        max = (long) StatUtils.max(times);
//        double sum = 0;
//        for (double d : times)
//            sum += d;
//        avg = sum / times.length;
//        p50 = (long) StatUtils.percentile(times, 50.0);
//        p90 = (long) StatUtils.percentile(times, 90.0);
//        p95 = (long) StatUtils.percentile(times, 95.0);
//        p99 = (long) StatUtils.percentile(times, 99.0);
//        StandardDeviation stdDev = new StandardDeviation();
//        stddev = (long) stdDev.evaluate(times, avg);
//        this.duration = duration;
//        this.maxUsers = maxUsers;
//        rps = (count - errorCount) / duration;
//        startDate = getDateFromInstant(start);
//        successCount = count - errorCount;
//    }

//    public void setSimulationName(String name) {
//        simulation = name;
//    }

//    public String average() {
//        return String.format(Locale.ENGLISH, "%.1f", avg);
//    }

//    public String boxpoints() {
//        if (count < MAX_BOXPOINT) {
//            return "'all'";
//        }
//        return "false";
//    }

//    public String throughput() {
//        return String.format(Locale.ENGLISH, "%.1f", rps);
//    }

//    public String percentError() {
//        if (count == 0) {
//            return "0.00";
//        }
//        return String.format(Locale.ENGLISH, "%.2f", (errorCount * 100.0) / count);
//    }

//    protected String getDateFromInstant(long start) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd " + "HH:mm:ss")
//                .withZone(ZoneId.systemDefault());
//        return formatter.format(Instant.ofEpochMilli(start));
//    }

    public double[] getDurationsAsArray() {
        double[] ret = new double[durations.size()];
        for (int i = 0; i < durations.size(); i++) {
            ret[i] = durations.get(i);
        }
        Arrays.sort(ret);
        return ret;
    }

//    public String getDuration() {
//        return String.format(Locale.ENGLISH, "%.1f", duration);
//    }
//
//    public List<String> getResults() {
//        return List.of(String.format(Locale.ENGLISH,
//                        "%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%.2f,%.2f,%s",
//                        simulation, scenario, maxUsers, request, start, startDate, duration, end, count, successCount,
//                        errorCount, min, p50, p90, p95, p99, max, avg, stddev, rps)
//                .split(","));
//    }
//
//    @Override
//    public String toString() {
//        return String.format(Locale.ENGLISH,
//                "%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%.2f,%.2f,%s",
//                simulation, scenario, maxUsers, request, start, startDate, duration, end, count, successCount,
//                errorCount, min, p50, p90, p95, p99, max, avg, stddev, rps);
//    }
//
    public String getSimulation() {
        return simulation;
    }
//
//    public double getAvg() {
//        return avg;
//    }
//
//    public double duration() {
//        return duration;
//    }
//
    public String getScenario() {
        return scenario;
    }
//
//    public void setScenario(String name) {
//        scenario = name;
//    }
//
//    public int getMaxUsers() {
//        return maxUsers;
//    }
//
//    public long getSuccessCount() {
//        return successCount;
//    }
//
    public long getErrorCount() {
        return errorCount;
    }
//
    public String getRequest() {
        return request;
    }
//
    public long getStart() {
        return start;
    }
//
//    public void setStart(long start) {
//        this.start = start;
//    }
//
    public long getEnd() {
        return end;
    }
//
//    public String getStartDate() {
//        return startDate;
//    }
//
//    public long getMin() {
//        return min;
//    }
//
//    public long getMax() {
//        return max;
//    }
//
//    public long getStddev() {
//        return stddev;
//    }
//
//    public long getP50() {
//        return p50;
//    }
//
//    public long getP90() {
//        return p90;
//    }
//
//    public long getP95() {
//        return p95;
//    }
//
//    public long getP99() {
//        return p99;
//    }
//
//    public double getRps() {
//        return rps;
//    }

    public long getCount() {
        return count;
    }

    public List<Double> getDurations() {
        return durations;
    }

    public int getIndice() {
        return indice;
    }


}
