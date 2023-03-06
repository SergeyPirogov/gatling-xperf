package io.xperf.reports;

import io.xperf.model.RequestStats;
import io.xperf.model.SimulationStats;
import net.quux00.simplecsv.CsvWriter;
import net.quux00.simplecsv.CsvWriterBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CsvReport extends Report {
    private final CsvWriter csvWriter;
    private final String filePath;
    private final boolean isShort;

    private CsvReport(String name, boolean isShort) {
        this.filePath = buildPath(name);
        this.isShort = isShort;

        FileWriter writer = getWriter(filePath);
        this.csvWriter = new CsvWriterBuilder(writer)
                .quoteChar(CsvWriter.NO_QUOTE_CHARACTER)
                .separator(',')
                .build();
    }

    public static CsvReport of(String name, boolean isShort) {
        return new CsvReport(name, isShort);
    }

    private static FileWriter getWriter(String name) {
        try {
            return new FileWriter(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String requestStatsToString(RequestStats requestStats) {
        if (isShort) {
            return String.format("%s,%s,%s,%s,%s,%.3f,%s,%s,%s,%s,%s,%s,%.0f,%s",
                    requestStats.getSimulationName(),
                    requestStats.getRequestName(),
                    requestStats.getCount(),
                    requestStats.getSuccessCount(),
                    requestStats.getErrorCount(),
                    requestStats.getRps(),
                    requestStats.getMin(),
                    requestStats.getP50(),
                    requestStats.getP90(),
                    requestStats.getP95(),
                    requestStats.getP99(),
                    requestStats.getMax(),
                    requestStats.getAvg(),
                    requestStats.getStddev());
        } else {
            return String.format("%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%.3f,%s,%s,%s,%s,%s,%s,%.0f,%s",
                    requestStats.getSimulationName(),
                    requestStats.getScenario(),
                    requestStats.getMaxUsers(),
                    requestStats.getRequestName(),
                    requestStats.getStart(),
                    requestStats.getStartDate(),
                    requestStats.getDuration(),
                    requestStats.getEnd(),
                    requestStats.getCount(),
                    requestStats.getSuccessCount(),
                    requestStats.getErrorCount(),
                    requestStats.getRps(),
                    requestStats.getMin(),
                    requestStats.getP50(),
                    requestStats.getP90(),
                    requestStats.getP95(),
                    requestStats.getP99(),
                    requestStats.getMax(),
                    requestStats.getAvg(),
                    requestStats.getStddev());
        }
    }

    private String buildPath(String fileName) {
        String name = fileName.replace(".log", "");
        Timestamp ts = Timestamp.from(Instant.now());
        return ts.getTime() + "_" + name + ".csv";
    }

    public List<String> getHeader() {
        if (isShort) {
            return List.of(
                    "simulation",
                    "scenario",
                    "total",
                    "ok",
                    "ko",
                    "rps",
                    "min",
                    "p50",
                    "p75",
                    "p95",
                    "p99",
                    "max",
                    "mean",
                    "stddev");
        } else {
            return List.of(
                    "simulation",
                    "scenario",
                    "maxUsers",
                    "request",
                    "start",
                    "startDate",
                    "duration",
                    "end",
                    "total",
                    "ok",
                    "ko",
                    "rps",
                    "min",
                    "p50", "p75", "p95", "p99", "max", "mean", "stddev");
        }
    }

    public String saveReport(SimulationStats stats) {
        List<List<String>> data = new ArrayList<>();
        List<String> header = List.of(String.join(",", getHeader()));
        data.add(header);

        stats.getResults().forEach(it -> {
            data.add(List.of(requestStatsToString(it)));
        });

        data.forEach(it -> it.forEach(System.out::println));

        writeData(data);
        return this.filePath;
    }

    private void writeData(List<List<String>> data) {
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
}