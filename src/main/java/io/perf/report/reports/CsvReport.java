package io.perf.report.reports;

import io.perf.report.context.SimulationContext;
import io.perf.report.model.RequestStat;
import net.quux00.simplecsv.CsvWriter;
import net.quux00.simplecsv.CsvWriterBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReport extends Report {
    private final static CsvWriter csvWriter = init();

    private static CsvWriter init() {
        FileWriter writer = getWriter();
        return new CsvWriterBuilder(writer)
                .quoteChar(CsvWriter.NO_QUOTE_CHARACTER)
                .separator(',')
                .build();
    }

    public static void saveReport(List<SimulationContext> stats) {
        List<List<String>> data = new ArrayList<>();
        List<String> header = RequestStat.header();
        data.add(header);

        stats.forEach(it -> {
            data.add(it.getSimulationStats().getResults());
            it.getRequestStats().forEach(requestStat -> data.add(requestStat.getResults()));
        });

        writeData(data);
    }

    private static void writeData(List<List<String>> data) {
        try {
            csvWriter.writeAll(data);
        } finally {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static FileWriter getWriter() {
        try {
            return new FileWriter("perf_report.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
