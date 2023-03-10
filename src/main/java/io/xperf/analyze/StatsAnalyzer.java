package io.xperf.analyze;

import io.xperf.context.Simulation;
import io.xperf.model.Apdex;
import io.xperf.model.RequestStats;
import io.xperf.model.SimulationRequest;
import io.xperf.model.SimulationStats;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StatsAnalyzer {

    public static SimulationStats computeSimulationStats(Simulation simulation) {
        List<SimulationRequest> requests = simulation.getRequests();
        SimulationStats analyticsResult = new SimulationStats();

        requests.forEach(req -> {
            int maxUsers = simulation.getMaxUsers(req.getScenario());
            RequestStats requestStatistics = computeRequestStats(req, maxUsers);
            requestStatistics.setRequestName(req.getRequest());
            requestStatistics.setIndice(req.getIndice());
            requestStatistics.setSimulationName(req.getSimulation());
            requestStatistics.setScenario(req.getScenario());
            requestStatistics.setStart(req.getStart());
            requestStatistics.setEnd(req.getEnd());

            analyticsResult.addResult(requestStatistics);
        });

        List<RequestStats> results = analyticsResult.getResults();
        results.sort(Comparator.comparing(RequestStats::getIndice));

        RequestStats start = results.get(0);
        RequestStats finish = results.get(results.size() - 1);

        double allRequestsDuration = Math.round(((finish.getEnd() - start.getStart()) / 1000.0));
        results.forEach(r -> {
            double rps = r.getCount() / allRequestsDuration;
            r.setRps(rps);
        });

        return analyticsResult;
    }

    private static RequestStats computeRequestStats(SimulationRequest request, int maxUsers) {
        double[] times = request.getDurationsAsArray();
        long min = (long) AnalyzerMath.getMin(times);
        long max = (long) AnalyzerMath.getMax(times);

        double sum = 0;
        for (double d : times)
            sum += d;

        double avg = sum / times.length;

        long p50 = Math.round(AnalyzerMath.percentile(times, 0.50));
        long p75 = Math.round(AnalyzerMath.percentile(times, 0.75));
        long p95 = Math.round(AnalyzerMath.percentile(times, 0.95));
        long p99 = Math.round(AnalyzerMath.percentile(times, 0.99));

        long stddev = Math.round(AnalyzerMath.calculateStandardDeviation(times));
        double duration = (request.getEnd() - request.getStart()) / 1000.0;

        String startDate = getDateFromInstant(request.getStart());
        long successCount = request.getCount() - request.getErrorCount();
        double rps = successCount / duration;

        Apdex apdex = calculateApdex(times);

        RequestStats requestStatistics = new RequestStats();
        requestStatistics.setMin(min);
        requestStatistics.setMax(max);
        requestStatistics.setAvg(avg);
        requestStatistics.setP50(p50);
        requestStatistics.setP75(p75);
        requestStatistics.setP95(p95);
        requestStatistics.setP99(p99);
        requestStatistics.setStddev(stddev);
        requestStatistics.setDuration(duration);
        requestStatistics.setMaxUsers(maxUsers);
        requestStatistics.setRps(rps);
        requestStatistics.setStartDate(startDate);
        requestStatistics.setSuccessCount(successCount);
        requestStatistics.setCount(request.getCount());
        requestStatistics.setErrorCount(request.getErrorCount());
        requestStatistics.setApdex(apdex);

        return requestStatistics;
    }

    private static Apdex calculateApdex(double[] times) {
        Apdex apdex = new Apdex();
        Arrays.stream(times).boxed().collect(Collectors.toList()).forEach(time -> apdex.add(time / 1000.0));
        return apdex;
    }

    private static String getDateFromInstant(long start) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd " + "HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return formatter.format(Instant.ofEpochMilli(start));
    }
}
