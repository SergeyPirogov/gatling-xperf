package io.perf.report.reports;

import io.perf.report.model.RequestStats;
import io.perf.report.model.SimulationStats;
import net.quux00.simplecsv.CsvWriter;
import net.quux00.simplecsv.CsvWriterBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReport extends Report {
    private final static CsvWriter csvWriter = init();

    public static List<String> getHeader() {
        return List.of(("simulation,scenario,maxUsers,request,start,startDate,duration,end,total,ok," +
                "ko,rps,min,p50,p75,p95,p99,max,mean,stddev")
                .split(","));
    }

    private static CsvWriter init() {
        FileWriter writer = getWriter();
        return new CsvWriterBuilder(writer)
                .quoteChar(CsvWriter.NO_QUOTE_CHARACTER)
                .separator(',')
                .build();
    }

    public static void saveReport(SimulationStats stats) {
        List<List<String>> data = new ArrayList<>();
        List<String> header = getHeader();
        data.add(header);

        stats.getResults().forEach(it -> {
            data.add(List.of(CsvReport.requestStatsToString(it)));
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

    public static String requestStatsToString(RequestStats requestStats) {
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

//        return String.format(Locale.ENGLISH,
//                "%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%.2f,%.2f,%s",
//                simulation, scenario, maxUsers, request, start, startDate, duration, end, count, successCount,
//                errorCount, min, p50, p90, p95, p99, max, avg, stddev, rps);
//        return String.format(Locale.ENGLISH,
//                "%s,%s,%s,%s,%s,%s,%.2f,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%.2f,%s,%.2f",
//                );
    }
}
