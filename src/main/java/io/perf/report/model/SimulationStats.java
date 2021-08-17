package io.perf.report.model;

import java.util.ArrayList;
import java.util.List;

public class SimulationStats {

    private final List<RequestStats> results = new ArrayList<>();

    public List<RequestStats> getResults() {
        return results;
    }

    public void addResult(RequestStats result) {
        this.results.add(result);
    }
}
