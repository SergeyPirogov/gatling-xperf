package io.xperf.test;

import io.xperf.PerfReportCli;
import org.junit.jupiter.api.Test;


public class TestCli {

//    @Test
//    public void testComparator(){
//        String[] args = { "-f", "examples/simulation.log", "-c", "examples/simulation2.log"};
//
//        PerfReportCli.main(args);
//    }


    @Test
    public void testCli(){
        String[] args = { "-f", "D:\\Gatling\\gatling-xperf\\examples\\simulation.log"};

        PerfReportCli.main(args);
    }
}
