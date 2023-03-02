package io.perf.report;

import io.perf.report.commands.AnalyzeCommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "perf_report", mixinStandardHelpOptions = true,
        subcommands = {AnalyzeCommand.class}
)
public class MainCommand implements Runnable{

    @Override
    public void run() {
    }

}
