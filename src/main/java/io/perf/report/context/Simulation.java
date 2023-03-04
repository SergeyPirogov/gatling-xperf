package io.perf.report.context;

import io.perf.report.model.SimulationRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class Simulation {
    public static final String ALL_REQUESTS = "_all";

    protected final String filePath;

    protected final Map<String, SimulationRequest> requests = new HashMap<>();

    protected final Map<String, CountMax> users = new HashMap<>();

    protected String simulationName;

    protected String scenarioName;

    //protected List<String> scripts = new ArrayList<>();

    protected long start;

    public Simulation(String filePath) {
        this.filePath = filePath;
        //requests.put(ALL_REQUESTS, new SimulationRequest(ALL_REQUESTS, ALL_REQUESTS, ALL_REQUESTS, 0));
    }

    public void setSimulationName(String name) {
        this.simulationName = name;
    }

    public List<SimulationRequest> getRequests() {
        return new ArrayList<>(requests.values());
    }

    //    public List<SimulationRequest> getRequests() {
//        List<SimulationRequest> ret = new ArrayList<>(requests.values());
//        ret.sort((a, b) -> (int) (1000 * (a.getAvg() - b.getAvg())));
//        return ret;
//    }

    public void addRequest(String scenario, String requestName, long start, long end, boolean success) {
        SimulationRequest request = requests.computeIfAbsent(requestName,
                name -> new SimulationRequest(simulationName, scenario, name, this.start));
        request.add(start, end, success);
        //simulationRequest.add(start, end, success);
    }

    public int getMaxUsers(String scenarioName) {
        CountMax countMax = users.get(scenarioName);
        if (countMax == null) {
            return users.values().stream().mapToInt(CountMax::getMax).sum();
        } else {
            return countMax.getMaximum();
        }
    }

    public Map<String, CountMax> getUsers() {
        return users;
    }

    public void computeStat() {
        //maxUsers = users.values().stream().mapToInt(CountMax::getMax).sum();
        //simStat.computeStat(maxUsers);
        //reqStats.values()
        //        .forEach(request -> request.computeStat(simStat.duration(), users.get(request.getScenario()).maximum));
    }

    public void setScenarioName(String name) {
        this.scenarioName = name;
        //simStat.setScenario(name);
    }

    public void setStart(long start) {
        this.start = start;
        //simStat.setStart(start);
    }

//    public Simulation setScripts(List<String> scripts) {
//        this.scripts = scripts;
//        return this;
//    }

//    public List<SimulationRequest> getRequestStats() {
//        return getRequests();
//    }


//    public Simulation setMaxUsers(int maxUsers) {
//        this.maxUsers = maxUsers;
//        return this;
//    }

    public void addUser(String scenario) {
        CountMax count = users.computeIfAbsent(scenario, k -> new CountMax());
        count.incr();
    }

    public void endUser(String scenario) {
        CountMax count = users.get(scenario);
        if (count != null) {
            count.decr();
        }
    }

//    public RequestStat getSimulationStats() {
//        return simStat;
//    }

    public static class CountMax {
        private int current = 0, maximum = 0;

        public void incr() {
            current += 1;
            maximum = max(current, maximum);
        }

        public void decr() {
            current -= 1;
        }

        public int getMax() {
            return maximum;
        }

        public int getMaximum() {
            return maximum;
        }
    }

}
