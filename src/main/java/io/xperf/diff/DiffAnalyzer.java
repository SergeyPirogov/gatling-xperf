package io.xperf.diff;


import com.github.freva.asciitable.AsciiTable;
import io.xperf.model.RequestStats;
import io.xperf.model.SimulationStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.xperf.reports.ConsoleReport.column;

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

        List<List<String>> rows = new ArrayList<>();

        for (int i = 0; i < baseSimulationStatsResults.size(); i++) {
            RequestStats baseRequestStats = baseSimulationStatsResults.get(i);

            if (i >= challengerSimulationStatsResults.size()) {
                break;
            }
            RequestStats challengerRequestStats = challengerSimulationStatsResults.get(i);

            long countUsersLeft = baseRequestStats.getMaxUsers();
            long countUsersRight = challengerRequestStats.getMaxUsers();
            Diff usersDiff = Diff.of(countUsersLeft, countUsersRight);

            long leftSuccessCount = baseRequestStats.getSuccessCount();
            long rightSuccessCount = challengerRequestStats.getSuccessCount();
            Diff successDiff = Diff.of(leftSuccessCount, rightSuccessCount);

            long leftErrorCount = baseRequestStats.getErrorCount();
            long rightErrorCount = challengerRequestStats.getErrorCount();
            Diff errorsDiff = Diff.of(leftErrorCount, rightErrorCount);

            long p95Left = baseRequestStats.getP95();
            long p95Right = challengerRequestStats.getP95();
            Diff p95Diff = Diff.of(p95Left, p95Right);

            long p99Left = baseRequestStats.getP99();
            long p99Right = challengerRequestStats.getP99();
            Diff p99Diff = Diff.of(p99Left, p99Right);

            long stdDevLeft = baseRequestStats.getStddev();
            long stdDevRight = challengerRequestStats.getStddev();
            Diff stdDiff = Diff.of(stdDevLeft, stdDevRight);

            rows.add(List.of(
                    baseRequestStats.getRequestName(),
                    usersDiff.toString(),
                    successDiff.toString(),
                    errorsDiff.toString(),
                    p95Diff.toString(true),
                    p99Diff.toString(true),
                    stdDiff.toString(true)
            ));
        }

        String table = AsciiTable.getTable(AsciiTable.FANCY_ASCII, rows, Arrays.asList(
                column("request").with(data -> data.get(0)),
                column("users").with(data -> data.get(1)),
                column("ok").with(data -> data.get(2)),
                column("ko").with(data -> data.get(3)),
                column("p95").with(data -> data.get(4)),
                column("p99").with(data -> data.get(5)),
                column("stddev").with(data -> data.get(6))
        ));

        System.out.println(table);
        System.out.println("=== END ===");
    }


}
