package io.perf.report.analyze;

import io.perf.report.context.Simulation;
import io.perf.report.model.SimulationRequest;

import java.util.List;

public class StatsAnalyzer {

    public static void computeSimulationStats(Simulation simulation) {
        List<SimulationRequest> requests = simulation.getRequests();
        System.out.println();
        requests.forEach(req -> {
            System.out.println();
            computeRequestStats(req);
        });
    }

    private static void computeRequestStats(SimulationRequest request) {

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
}
