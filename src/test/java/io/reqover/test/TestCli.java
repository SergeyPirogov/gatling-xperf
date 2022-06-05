package io.reqover.test;

import io.reqover.MainCommand;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

public class TestCli {

    @Test
    public void testCli(){
        String[] args = { "upload" , "-s", "http://reqover-io.herokuapp.com", "-t", "z0eczbrvs2ae"};

        final CommandLine cmd = new CommandLine(new MainCommand());
        final CommandLine.ParseResult parseResult = cmd.parseArgs(args);
        int exitCode = cmd.execute(args);

//        assertThat()
//        assert !cmd.isUsageHelpRequested();
//        assert  tar.create;
//        assert  tar.archive.equals(new File("result.tar"));
//        assert  Arrays.equals(tar.files, new File[] {new File("file1.txt"), new File("file2.txt")});
    }
}
