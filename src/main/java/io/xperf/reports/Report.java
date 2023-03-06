package io.xperf.reports;

import io.xperf.model.SimulationStats;

public abstract class Report {

    public abstract String processReport(SimulationStats stats);
}
