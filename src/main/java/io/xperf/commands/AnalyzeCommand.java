package io.xperf.commands;

import io.xperf.analyze.StatsAnalyzer;
import io.xperf.context.Simulation;
import io.xperf.diff.DiffAnalyzer;
import io.xperf.model.RequestStats;
import io.xperf.model.SimulationStats;
import io.xperf.parser.ParserFactory;
import io.xperf.parser.SimulationParser;
import io.xperf.reports.ConsoleReport;
import io.xperf.reports.CsvReport;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CommandLine.Command(
        name = "analyze", mixinStandardHelpOptions = true,
        description = "analyze files"
)
public class AnalyzeCommand implements Runnable {

    @CommandLine.Option(names = {"-f", "--file"}, paramLabel = "simulation.log", description = "the files", required = true)
    private File simulationFile;

    @CommandLine.Option(names = {"-c", "--challenger"}, paramLabel = "challenger.log", description = "the files")
    private File challengerFile;

    @CommandLine.Option(names = {"-s", "--short"}, paramLabel = "DIR", description = "short output")
    private boolean isShort;

    @CommandLine.Option(names = {"-v", "--verbose"}, paramLabel = "DIR", description = "short output")
    private boolean isVerbose;

    @CommandLine.Option(names = {"-o", "--output-dir"}, paramLabel = "DIR", description = "output dir")
    private String outputFolder = ".";

    @CommandLine.Option(names = {"--fail-if-apdex"}, paramLabel = "DIR", description = "output dir")
    private String assertApdex = "unacceptable";

    @Override
    public void run() {
        SimulationStats baseSimulationStats = analyze(simulationFile);
        processCsvReport(baseSimulationStats, simulationFile.getName(), outputFolder, isShort);
        processConsoleReport(baseSimulationStats, isVerbose, isShort);

        if (challengerFile != null) {
            SimulationStats challengerSimulationStats = analyze(challengerFile);
            processCsvReport(challengerSimulationStats, challengerFile.getName(), outputFolder, isShort);
            processConsoleReport(challengerSimulationStats, isVerbose, isShort);
            DiffAnalyzer.computeDiff(baseSimulationStats, challengerSimulationStats);
            assertOnApdex(challengerSimulationStats, assertApdex);
        }

        assertOnApdex(baseSimulationStats, assertApdex);
    }

    private SimulationStats analyze(File file) {
        Simulation simulation = parseSimulationFile(file);
        return StatsAnalyzer.computeSimulationStats(simulation);
    }

    protected Simulation parseSimulationFile(File file) {
        final long startTime = System.currentTimeMillis();
        System.out.println("=== Parsing " + file.getAbsolutePath());

        try {
            SimulationParser parser = ParserFactory.getParser(file);
            Simulation simulation = parser.parse();
            final long endTime = System.currentTimeMillis();
            System.out.println("Parsing finished in " + (endTime - startTime) + " ms.");
            return simulation;
        } catch (IOException e) {
            System.err.println("Invalid file: " + file.getAbsolutePath() + " " + e);
            throw new RuntimeException(e);
        }
    }

    private void processConsoleReport(SimulationStats stats, boolean isVerbose, boolean isShort) {
        if (isVerbose) {
            ConsoleReport.of(isShort).processReport(stats);
        }
    }

    private String processCsvReport(SimulationStats stats, String name, String outputFolder, boolean isShort) {
        String reportPath = CsvReport.of(outputFolder, name, isShort).processReport(stats);
        System.out.printf("Report is saved in %s\n\n", reportPath);
        return reportPath;
    }

    private void assertOnApdex(SimulationStats baseSimulationStats, String assertApdex) {
        List<RequestStats> requestStats = baseSimulationStats.getResults()
                .stream()
                .filter(it -> it.getApdex()
                        .getRating()
                        .name().toLowerCase()
                        .equals(assertApdex))
                .collect(Collectors.toList());

        if (requestStats.size() <= 0) {
            System.exit(0);
        }
        System.out.println("=== Apdex " + assertApdex + " rating violations");
        requestStats.forEach(it -> {
            System.out.println(it.getRequestName() + " score " + String.format("%.2f", it.getApdex().getScore()));
        });
        System.out.println("===");
        System.exit(1);
    }
}
