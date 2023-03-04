package io.perf.report.commands;

import io.perf.report.analyze.StatsAnalyzer;
import io.perf.report.context.Simulation;
import io.perf.report.model.SimulationStats;
import io.perf.report.parser.ParserFactory;
import io.perf.report.parser.SimulationParser;
import io.perf.report.reports.CsvReport;
import org.apache.log4j.Logger;
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

    @CommandLine.Option(names = {"-f", "--files"}, paramLabel = "FILES", description = "the files")
    private File file;

    @CommandLine.Option(names = {"-o", "--output-dir"}, paramLabel = "DIR", description = "output dir")
    private String dirName;

    @Override
    public void run() {
//        files.forEach(it -> {
            System.out.println(file.getName());
            Simulation simulation = parseSimulationFile(file);
            SimulationStats analyticsResult = StatsAnalyzer.computeSimulationStats(simulation);

            generateCsvReport(analyticsResult, file.getName());
        //});
    }

    protected Simulation parseSimulationFile(File file) {
        final long startTime = System.currentTimeMillis();
        log.info("Parsing " + file.getAbsolutePath());

        try {
            SimulationParser parser = ParserFactory.getParser(file);
            Simulation simulation = parser.parse();
            final long endTime = System.currentTimeMillis();
            log.info("Parsing finished in " + (endTime - startTime) + " ms. File " + file.getAbsolutePath());
            return simulation;
        } catch (IOException e) {
            log.error("Invalid file: " + file.getAbsolutePath(), e);
            throw new RuntimeException(e);
        }
    }

    protected void generateCsvReport(SimulationStats stats, String name) {
        CsvReport.of(name).saveReport(stats);
    }
}
