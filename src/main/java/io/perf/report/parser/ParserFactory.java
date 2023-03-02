package io.perf.report.parser;

import io.perf.report.parser.impl.*;
import io.perf.report.reader.SimulationReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ParserFactory {

    public static SimulationParser getParser(File file, Float apdexT) throws IOException {
        return getVersionSpecificParser(file, apdexT);
    }

    public static SimulationParser getParser(File file) throws IOException {
        return getVersionSpecificParser(file, null);
    }

    protected static SimulationParser getVersionSpecificParser(File file, Float apdexT) throws IOException {
        List<String> header = getHeaderLine(file);
        if (header.size() == 6) {
            String version = header.get(5);
            if (version.matches("3\\.[2-3].*")) {
                return new SimulationParserV32(file, apdexT);
            }
            if (version.startsWith("3.4")) {
                return new SimulationParserV34(file, apdexT);
            }
            if (version.matches("3\\.[5-9].*")) {
                return new SimulationParserV35(file, apdexT);
            }
            if (version.startsWith("3.0")) {
                return new SimulationParserV3(file, apdexT);
            }
            if (version.startsWith("2.")) {
                return new SimulationParserV2(file, apdexT);
            }
        } else if (header.size() == 7) {
            String version = header.get(6);
            if (version.startsWith("2.")) {
                return new SimulationParserV23(file, apdexT);
            }
        }
        throw new IllegalArgumentException("Unknown Gatling simulation version: " + header);
    }

    protected static List<String> getHeaderLine(File file) throws IOException {
        try (SimulationReader reader = new SimulationReader(file)) {
            return reader.readNext();
        }
    }
}
