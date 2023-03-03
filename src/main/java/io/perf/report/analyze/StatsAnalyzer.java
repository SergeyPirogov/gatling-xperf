package io.perf.report.analyze;

import io.perf.report.context.Simulation;
import io.perf.report.model.AnalyticsResult;
import io.perf.report.model.SimulationRequest;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class StatsAnalyzer {

    public static void computeSimulationStats(Simulation simulation) {
        List<SimulationRequest> requests = simulation.getRequests();
        System.out.println();
        Map<String, Simulation.CountMax> users = simulation.getUsers();

        AnalyticsResult analyticsResult = new AnalyticsResult();

        requests.forEach(req -> {
            computeRequestStats(req, users.get(req.getScenario()).getMaximum());
//            analyticsResult.setResult();
        });
    }

//    private static void computeRequestStats(SimulationRequest request, int maxUsers) {
//        computeStat(request,  maxUsers);
//    }

//    private static void computeRequestStats(SimulationRequest request, int maxUsers) {
//        computeStat(, maxUsers);
//    }

    private static void computeRequestStats(SimulationRequest request, int maxUsers) {
        double[] times = request.getDurationsAsArray();
        long min = (long) StatUtils.min(times);
        long max = (long) StatUtils.max(times);

        double sum = 0;
        for (double d : times)
            sum += d;

        double avg = sum / times.length;
        long p50 = (long) StatUtils.percentile(times, 50.0);
        long p90 = (long) StatUtils.percentile(times, 90.0);
        long p95 = (long) StatUtils.percentile(times, 95.0);
        long p99 = (long) StatUtils.percentile(times, 99.0);

        StandardDeviation stdDev = new StandardDeviation();
        long stddev = (long) stdDev.evaluate(times, avg);

        double duration = (request.getEnd() - request.getStart()) / 1000.0;
        //this.maxUsers = maxUsers;
        double rps = (request.getCount() - request.getErrorCount()) / duration;
        String startDate = getDateFromInstant(request.getStart());
        long successCount = request.getCount() - request.getErrorCount();
    }

    private static String getDateFromInstant(long start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd " + "HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(Instant.ofEpochMilli(start));
    }
}
