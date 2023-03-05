package io.perf.report.commands;

import io.perf.report.analyze.StatsAnalyzer;
import io.perf.report.context.Simulation;
import io.perf.report.model.SimulationStats;
import io.perf.report.parser.ParserFactory;
import io.perf.report.parser.SimulationParser;
import io.perf.report.reports.CsvReport;
import org.apache.log4j.Logger;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.PropertyChange;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

//            Javers javers = JaversBuilder.javers().build();
//            Diff diff = javers.compare(baseSimulationStats, challengerSimulationStats);
//            List<PropertyChange> p99Changes = diff.getPropertyChanges("p99");
//
//            if (p99Changes.size() > 0) {
//                log.info("=== p99 changes ===");
//                p99Changes.forEach(change -> {
//                    log.info(change.getLeft() + " -> " + change.getRight());
//                });
//            }

        }
    }

    private SimulationStats analyze(File file) {
        Simulation simulation = parseSimulationFile(file);
        return StatsAnalyzer.computeSimulationStats(simulation);
    }

    protected Simulation parseSimulationFile(File file) {
        final long startTime = System.currentTimeMillis();
        log.info("Parsing " + file.getAbsolutePath());

        try {
            SimulationParser parser = ParserFactory.getParser(file);
            Simulation simulation = parser.parse();
            final long endTime = System.currentTimeMillis();
            log.info("Parsing finished in " + (endTime - startTime) + " ms.");
            return simulation;
        } catch (IOException e) {
            log.error("Invalid file: " + file.getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
    }

    protected String generateCsvReport(SimulationStats stats, String name, boolean isShort) {
        String reportPath = CsvReport.of(name, isShort).saveReport(stats);
        log.info("Report is saved in" + reportPath);
        return reportPath;
    }
}
