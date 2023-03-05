package io.perf.report.diff;

import de.vandermeer.asciitable.AT_Context;
import de.vandermeer.asciitable.AsciiTable;
import io.perf.report.model.RequestStats;
import io.perf.report.model.SimulationStats;

import java.util.ArrayList;
import java.util.List;

public class DiffAnalyzer {

    public static void computeDiff(SimulationStats baseSimulationStats, SimulationStats challengerSimulationStats) {
        List<RequestStats> baseSimulationStatsResults = baseSimulationStats.getResults();
        List<RequestStats> challengerSimulationStatsResults = challengerSimulationStats.getResults();
        System.out.println("=== Diff ===");

        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("Request", "min", "p95", "p99", "max");
        at.addRule();
        for (int i = 0; i < baseSimulationStatsResults.size(); i++) {
            RequestStats baseRequestStats = baseSimulationStatsResults.get(i);
            RequestStats challengerRequestStats = challengerSimulationStatsResults.get(i);

            long minLeft = baseRequestStats.getMin();
            long minRight = challengerRequestStats.getMin();

            long maxLeft = baseRequestStats.getMax();
            long maxRight = challengerRequestStats.getMax();

            long p95Left = baseRequestStats.getP95();
            long p95Right = challengerRequestStats.getP95();

            long p99Left = baseRequestStats.getP99();
            long p99Right = challengerRequestStats.getP99();

            long minDiff = calculatePercentDiffPercent(minLeft, minRight);
            long p95Diff = calculatePercentDiffPercent(p95Left, p95Right);
            long p99Diff = calculatePercentDiffPercent(p99Left, p99Right);
            long maxDiff = calculatePercentDiffPercent(maxLeft, maxRight);

            at.addRow(baseRequestStats.getRequestName(),
                    minLeft + "->" + minRight + " (" + minDiff + "%)",
                    p95Left + "->" + p95Right + " (" + p95Diff + "%)",
                    p99Left + "->" + p99Right + " (" + p99Diff + "%)",
                    maxLeft + "->" + maxRight + " (" + maxDiff + "%)"
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
