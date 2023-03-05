package io.perf.report.commands;

import io.perf.report.analyze.StatsAnalyzer;
import io.perf.report.context.Simulation;
import io.perf.report.diff.DiffAnalyzer;
import io.perf.report.model.SimulationStats;
import io.perf.report.parser.ParserFactory;
import io.perf.report.parser.SimulationParser;
import io.perf.report.reports.CsvReport;
import org.apache.log4j.Logger;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
        name = "analyze", mixinStandardHelpOptions = true,
        description = "analyze files"
)
public class AnalyzeCommand implements Runnable {
    private final static Logger log = Logger.getLogger(AnalyzeCommand.class);

    @CommandLine.Option(names = {"-f", "--file"}, paramLabel = "simulation.log", description = "the files", required = true)
    private File simulationFile;

    @CommandLine.Option(names = {"-c", "--challenger"}, paramLabel = "challenger.log", description = "the files")
    private File challengerFile;

    @CommandLine.Option(names = {"-s", "--short"}, paramLabel = "DIR", description = "short output")
    private boolean isShort;

//    @CommandLine.Option(names = {"-o", "--output-dir"}, paramLabel = "DIR", description = "output dir")
//    private String dirName;

    @Override
    public void run() {
        SimulationStats baseSimulationStats = analyze(simulationFile);
        generateCsvReport(baseSimulationStats, simulationFile.getName(), isShort);

        if (challengerFile != null) {
            SimulationStats challengerSimulationStats = analyze(challengerFile);
            generateCsvReport(challengerSimulationStats, challengerFile.getName(), isShort);

            DiffAnalyzer.computeDiff(baseSimulationStats, challengerSimulationStats);
        }
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
            log.error("Invalid file: " + file.getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
    }

    protected String generateCsvReport(SimulationStats stats, String name, boolean isShort) {
        String reportPath = CsvReport.of(name, isShort).saveReport(stats);
        System.out.printf("Report is saved in %s\n\n", reportPath);
        return reportPath;
    }
}
