package io.perf.report.model;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsResult {

    private List<RequestStatistics> result = new ArrayList<>();

    public List<RequestStatistics> getResult() {
        return result;
    }

    public void setResult(List<RequestStatistics> result) {
        this.result = result;
    }
}
