
package io.perf.report.parser.impl;

import io.perf.report.parser.SimulationParser;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gatling 3.2 simulation format
 */
public class SimulationParserV32 extends SimulationParser {

    final protected Map<String, String> userScenario = new HashMap<>();

    public SimulationParserV32(File file) {
        super(file);
    }

    protected String getSimulationName(List<String> line) {
        return line.get(2);
    }

    protected String getSimulationStart(List<String> line) {
        return line.get(3);
    }

    protected String getScenario(List<String> line) {
        String user;
        if (USER.equals(line.get(0))) {
            user = line.get(2);
            if (START.equals(line.get(3))) {
                String ret = line.get(1);
                userScenario.put(user, ret);
            }
        } else if (RUN.equals(line.get(0))) {
            return line.get(1);
        } else {
            user = line.get(1);
        }
        return userScenario.get(user);
    }

    protected String getType(List<String> line) {
        return line.get(0);
    }

    protected String getUserType(List<String> line) {
        return line.get(3);
    }

    protected String getRequestName(List<String> line) {
        return line.get(3);
    }

    protected Long getRequestStart(List<String> line) {
        return Long.parseLong(line.get(4));
    }

    protected Long getRequestEnd(List<String> line) {
        return Long.parseLong(line.get(5));
    }

    protected boolean getRequestSuccess(List<String> line) {
        return OK.equals(line.get(6));
    }
}
