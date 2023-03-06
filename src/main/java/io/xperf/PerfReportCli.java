package io.xperf;

import io.xperf.commands.AnalyzeCommand;
import picocli.CommandLine;

import java.util.Arrays;

public class PerfReportCli {

    public static void main(String[] args) {
        CommandLine cmd = cmd(args);
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    public static CommandLine cmd(String[] args) {
        final CommandLine cmd = new CommandLine(new AnalyzeCommand());
        final CommandLine.ParseResult parseResult = cmd.parseArgs(args);
        if (!parseResult.errors().isEmpty()) {
            System.out.println(cmd.getUsageMessage());
        }
        return cmd;
    }
}
