package io.perf.report.commands;

import io.perf.report.context.SimulationContext;
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

    @Override
    public void run() {
        files.forEach(it -> {
            System.out.println(it.getName());
            List<SimulationContext> stats = parseSimulationFile(it);
            generateCsvReport(stats);
        });
    }

    protected List<SimulationContext> parseSimulationFile(File file) {
        final long startTime = System.currentTimeMillis();
        log.info("Parsing " + file.getAbsolutePath());
        List<SimulationContext> stats = new ArrayList<>();
        try {
            SimulationParser parser = ParserFactory.getParser(file);
            stats.add(parser.parse());
            final long endTime = System.currentTimeMillis();
            log.info("Parsing finished in " + (endTime - startTime) + " ms. File " + file.getAbsolutePath());
        } catch (IOException e) {
            log.error("Invalid file: " + file.getAbsolutePath(), e);
        }
        return stats;
    }

    protected void generateCsvReport(List<SimulationContext> stats) {
        CsvReport.saveReport(stats);
    }
}
