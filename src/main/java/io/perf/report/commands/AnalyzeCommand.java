package io.perf.report.commands;

import io.perf.report.context.Simulation;
import io.perf.report.parser.ParserFactory;
import io.perf.report.parser.impl.SimulationParser;
import io.perf.report.reports.CsvReport;
import org.apache.log4j.Logger;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CommandLine.Command(
        name = "analyze", mixinStandardHelpOptions = true,
        description = "analyze files"
)
public class AnalyzeCommand implements Runnable {
    private final static Logger log = Logger.getLogger(AnalyzeCommand.class);

    @CommandLine.Option(names = {"-f", "--files"}, paramLabel = "FILES", description = "the files")
    private List<File> files;

    @CommandLine.Option(names = {"-o", "--output-dir"}, paramLabel = "DIR", description = "output dir")
    private String dirName;

    @Override
    public void run() {
        files.forEach(it -> {
            System.out.println(it.getName());
            List<Simulation> simulations = parseSimulationFile(it);
            generateCsvReport(simulations);
        });
    }

    protected List<Simulation> parseSimulationFile(File file) {
        final long startTime = System.currentTimeMillis();
        log.info("Parsing " + file.getAbsolutePath());
        List<Simulation> simulations = new ArrayList<>();
        try {
            SimulationParser parser = ParserFactory.getParser(file);
            simulations.add(parser.parse());
            final long endTime = System.currentTimeMillis();
            log.info("Parsing finished in " + (endTime - startTime) + " ms. File " + file.getAbsolutePath());
        } catch (IOException e) {
            log.error("Invalid file: " + file.getAbsolutePath(), e);
        }
        return simulations;
    }

    protected void generateCsvReport(List<Simulation> stats) {
        CsvReport.saveReport(stats);
    }
}
