package io.xperf.test;

import io.xperf.PerfReportCli;
import org.junit.jupiter.api.Test;


public class TestCli {

    @Test
    public void testComparator(){
        String[] args = { "-f", "data/simulation.log", "-c", "data/simulation2.log"};

        PerfReportCli.main(args);
    }


    @Test
    public void testCli(){
        String[] args = { "-f", "data/simulation.log", "-v", "-s"};

        PerfReportCli.main(args);
    }
}
