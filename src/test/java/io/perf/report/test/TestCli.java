package io.perf.report.test;

import io.perf.report.MainCommand;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static io.perf.report.PerfReportCli.cmd;

public class TestCli {

    @Test
    public void testCli(){
        String[] args = { "analyze" , "-f", "simulation.log", "-o", "./report"};

        final CommandLine cmd = new CommandLine(new MainCommand());
        cmd.parseArgs(args);
        int exitCode = cmd.execute(args);

//        assertThat()
//        assert !cmd.isUsageHelpRequested();
//        assert  tar.create;
//        assert  tar.archive.equals(new File("result.tar"));
//        assert  Arrays.equals(tar.files, new File[] {new File("file1.txt"), new File("file2.txt")});
    }
}
