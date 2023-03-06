package io.xperf.reports;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import io.xperf.model.RequestStats;
import io.xperf.model.SimulationStats;

import java.util.Arrays;

public class ConsoleReport extends Report {

    public static Column column(String name) {
        return new Column().header(name).dataAlign(HorizontalAlign.LEFT);
    }

    public static ConsoleReport of() {
        return new ConsoleReport();
    }

    @Override
    public String processReport(SimulationStats stats) {
        String table = AsciiTable.getTable(AsciiTable.FANCY_ASCII, stats.getResults(), Arrays.asList(
                column("simulation").with(RequestStats::getSimulationName),
                column("scenario").with(RequestStats::getScenario),
                column("maxUsers").with(data -> String.valueOf(data.getMaxUsers())),
                column("request").with(RequestStats::getRequestName),
                column("start").with(data -> String.valueOf(data.getStart())),
                column("startDate").with(data -> String.valueOf(data.getStartDate())),
                column("duration").with(data -> String.valueOf(data.getDuration())),
                column("end").with(data -> String.valueOf(data.getEnd())),
                column("total").with(data -> String.valueOf(data.getCount())),
                column("ok").with(data -> String.valueOf(data.getSuccessCount())),
                column("ko").with(data -> String.valueOf(data.getErrorCount())),
                column("rps").with(data -> String.format("%.2f", data.getRps())),
                column("min").with(data -> String.valueOf(data.getMin())),
                column("p50").with(data -> String.valueOf(data.getP50())),
                column("p75").with(data -> String.valueOf(data.getP75())),
                column("p95").with(data -> String.valueOf(data.getP95())),
                column("p99").with(data -> String.valueOf(data.getP99())),
                column("max").with(data -> String.valueOf(data.getMax())),
                column("max").with(data -> String.valueOf(data.getMax())),
                column("stddev").with(data -> String.valueOf(data.getStddev()))
        ));

        System.out.println(table);
        System.out.println();
        return table;
    }
}
