package io.xperf.diff;

import de.vandermeer.asciitable.AsciiTable;
import io.xperf.model.RequestStats;
import io.xperf.model.SimulationStats;

import java.util.List;

public class DiffAnalyzer {

    public static void computeDiff(SimulationStats baseSimulationStats, SimulationStats challengerSimulationStats) {
        List<RequestStats> baseSimulationStatsResults = baseSimulationStats.getResults();
        List<RequestStats> challengerSimulationStatsResults = challengerSimulationStats.getResults();

        System.out.println("=== Diff ===");
        if (baseSimulationStatsResults.size() != challengerSimulationStatsResults.size()) {
            System.err.println("Simulation request results size is different!!!");
            System.out.printf("Base simulation size is %s, challenger size is %s.\n",
                    baseSimulationStatsResults.size(), challengerSimulationStatsResults.size());
            System.out.println("Diff will be computed for lowest size");
        }
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Request", "Max users", "OK", "KO", "p95", "p99", "stddev");
        at.addRule();
        for (int i = 0; i < baseSimulationStatsResults.size(); i++) {
            RequestStats baseRequestStats = baseSimulationStatsResults.get(i);

            if (i >= challengerSimulationStatsResults.size()) {
                break;
            }

            RequestStats challengerRequestStats = challengerSimulationStatsResults.get(i);

            long countUsersLeft = baseRequestStats.getMaxUsers();
            long countUsersRight = challengerRequestStats.getMaxUsers();

            long leftSuccessCount = baseRequestStats.getSuccessCount();
            long rightSuccessCount = challengerRequestStats.getSuccessCount();

            long leftErrorCount = baseRequestStats.getErrorCount();
            long rightErrorCount = challengerRequestStats.getErrorCount();

            long p95Left = baseRequestStats.getP95();
            long p95Right = challengerRequestStats.getP95();

            long p99Left = baseRequestStats.getP99();
            long p99Right = challengerRequestStats.getP99();

            long stddevLeft = baseRequestStats.getStddev();
            long stddevRight = challengerRequestStats.getStddev();

            long p95Diff = calculatePercentDiffPercent(p95Left, p95Right);
            long p99Diff = calculatePercentDiffPercent(p99Left, p99Right);
            long stddevDiff = calculatePercentDiffPercent(stddevLeft, stddevRight);

            at.addRow(baseRequestStats.getRequestName(),
                    countUsersLeft + "->" + countUsersRight,
                    leftSuccessCount + "->" + rightSuccessCount,
                    leftErrorCount + "->" + rightErrorCount,
                    p95Left + "->" + p95Right + " (" + p95Diff + "%)",
                    p99Left + "->" + p99Right + " (" + p99Diff + "%)",
                    stddevLeft + "->" + stddevRight + " (" + stddevDiff + "%)"

            );
            at.addRule();
        }
        String rend = at.render();
        System.out.println(rend);
        System.out.println("=== END ===");
    }

    private static long calculatePercentDiffPercent(long left, long right) {
        double res = ((double) (right - left) / right) * 100;
        return Math.round(res);
    }
}
