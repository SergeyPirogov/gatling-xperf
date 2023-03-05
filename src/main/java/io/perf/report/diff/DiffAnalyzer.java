package io.perf.report.diff;

import io.perf.report.model.RequestStats;
import io.perf.report.model.SimulationStats;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;

import java.util.List;

public class DiffAnalyzer {

    public static void computeDiff(SimulationStats baseSimulationStats, SimulationStats challengerSimulationStats) {
        Javers javers = JaversBuilder.javers().build();
        List<RequestStats> baseSimulationStatsResults = baseSimulationStats.getResults();
        List<RequestStats> challengerSimulationStatsResults = challengerSimulationStats.getResults();
        System.out.println("=== p99 diff ===");
        for (int i = 0; i < baseSimulationStatsResults.size(); i++) {
            RequestStats requestStats = baseSimulationStatsResults.get(i);
            Diff diff = javers.compare(requestStats, challengerSimulationStatsResults.get(i));
            List<PropertyChange> p99Changes = diff.getPropertyChanges("p99");

            if (p99Changes.size() > 0) {
                p99Changes.forEach(change -> {
                    System.out.println(requestStats.getRequestName() + ": [" + change.getLeft() + " -> " + change.getRight() + "]");
                });
            } else {
                System.out.println("No diff found");
            }
        }
        System.out.println("=== END ===");

    }
}
