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

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Request","p95", "p99", "stddev");
        at.addRule();
        for (int i = 0; i < baseSimulationStatsResults.size(); i++) {
            RequestStats baseRequestStats = baseSimulationStatsResults.get(i);
            RequestStats challengerRequestStats = challengerSimulationStatsResults.get(i);

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