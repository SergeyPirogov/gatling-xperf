

package io.xperf.parser.impl;

import io.xperf.parser.SimulationParser;

import java.io.File;
import java.util.List;

/**
 * Gatling 2.3.1 simulation format
 */
public class SimulationParserV23 extends SimulationParser {

    public SimulationParserV23(File file) {
        super(file);
    }

    protected String getSimulationName(List<String> line) {
        return line.get(3);
    }

    protected String getSimulationStart(List<String> line) {
        return line.get(4);
    }

    protected String getScenario(List<String> line) {
        return line.get(1);
    }

    protected String getType(List<String> line) {
        return line.get(0);
    }

    protected String getUserType(List<String> line) {
        return line.get(3);
    }

    protected String getRequestName(List<String> line) {
        return line.get(4);
    }

    protected Long getRequestStart(List<String> line) {
        return Long.parseLong(line.get(5));
    }

    protected Long getRequestEnd(List<String> line) {
        return Long.parseLong(line.get(6));
    }

    protected boolean getRequestSuccess(List<String> line) {
        return OK.equals(line.get(7));
    }
}
