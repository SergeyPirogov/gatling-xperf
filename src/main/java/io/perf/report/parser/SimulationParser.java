package io.perf.report.parser;


import io.perf.report.context.Simulation;
import io.perf.report.reader.SimulationReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class SimulationParser {

    protected static final String OK = "OK";

    protected static final String REQUEST = "REQUEST";

    protected static final String RUN = "RUN";

    protected static final String USER = "USER";

    protected static final String START = "START";

    protected static final String END = "END";

    protected final File file;

    public SimulationParser(File file) {
        this.file = file;
    }

    public Simulation parse() throws IOException {
        Simulation simulation = new Simulation(file.getAbsolutePath());
        try (SimulationReader reader = new SimulationReader(file)) {
            List<String> line;
            String name;
            String scenario;
            long start, end;
            boolean success;
            List<String> header = reader.readNext();
            checkLine(header);

            simulation.setSimulationName(getSimulationName(header));
            simulation.setScenarioName(getScenario(header));
            simulation.setStart(Long.parseLong(getSimulationStart(header)));

            while ((line = reader.readNext()) != null) {
                scenario = getScenario(line);

                switch (getType(line)) {
                    case RUN:
                        break;
                    case REQUEST:
                        name = getRequestName(line);
                        start = getRequestStart(line);
                        end = getRequestEnd(line);
                        success = getRequestSuccess(line);
                        simulation.addRequest(scenario, name, start, end, success);
                        break;
                    case USER:
                        switch (getUserType(line)) {
                            case START:
                                simulation.addUser(scenario);
                                break;
                            case END:
                                simulation.endUser(scenario);
                                break;
                        }
                        break;
                }
            }
        }

        return simulation;
    }

    protected void checkLine(List<String> line) {
        if (line.size() <= 2) {
            invalidFile();
        }
    }

    protected abstract String getSimulationName(List<String> line);

    protected abstract String getSimulationStart(List<String> line);

    protected abstract String getScenario(List<String> line);

    protected abstract String getType(List<String> line);

    protected abstract String getUserType(List<String> line);

    protected abstract String getRequestName(List<String> line);

    protected abstract Long getRequestStart(List<String> line);

    protected abstract Long getRequestEnd(List<String> line);

    protected abstract boolean getRequestSuccess(List<String> line);

    protected Simulation invalidFile() {
        throw new IllegalArgumentException(String.format(
                "Invalid simulation file: %s expecting " + "Gatling 2.1, 2.3.1 or 3.x format", file.getAbsolutePath()));
    }

}
