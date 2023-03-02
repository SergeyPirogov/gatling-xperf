
package io.perf.report.parser.impl;

import java.io.File;
import java.util.List;

/**
 * Gatling 2.1.7 simulation format
 */
public class SimulationParserV2 extends SimulationParser {

    public SimulationParserV2(File file, Float apdexT) {
        super(file, apdexT);
    }

    public SimulationParserV2(File file) {
        super(file);
    }

    protected String getSimulationName(List<String> line) {
        return line.get(1);
    }

    protected String getSimulationStart(List<String> line) {
        return line.get(3);
    }

    protected String getScenario(List<String> line) {
        return line.get(0);
    }

    protected String getType(List<String> line) {
        return line.get(2);
    }

    protected String getUserType(List<String> line) {
        return line.get(3);
    }

    protected String getRequestName(List<String> line) {
        return line.get(4);
    }

    protected Long getRequestStart(List<String> line) {
        return Long.parseLong(line.get(6));
    }

    protected Long getRequestEnd(List<String> line) {
        return Long.parseLong(line.get(8));
    }

    protected boolean getRequestSuccess(List<String> line) {
        return OK.equals(line.get(9));
    }
}
