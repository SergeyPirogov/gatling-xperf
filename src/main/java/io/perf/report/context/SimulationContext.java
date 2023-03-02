package io.perf.report.context;

import io.perf.report.model.RequestStat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class SimulationContext {
    public static final String ALL_REQUESTS = "_all";

    protected final Float apdexT;

    protected final String filePath;

    protected final RequestStat simStat;

    protected final Map<String, RequestStat> reqStats = new HashMap<>();

    protected final Map<String, CountMax> users = new HashMap<>();

    protected String simulationName;

    protected String scenarioName;

    protected List<String> scripts = new ArrayList<>();

    protected int maxUsers;

    protected long start;

    public SimulationContext(String filePath, Float apdexT) {
        this.filePath = filePath;
        this.simStat = new RequestStat(ALL_REQUESTS, ALL_REQUESTS, ALL_REQUESTS, 0, apdexT);
        this.apdexT = apdexT;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String name) {
        this.simulationName = name;
        simStat.setSimulationName(name);
    }

    public RequestStat getSimStat() {
        return simStat;
    }

    public List<RequestStat> getRequests() {
        List<RequestStat> ret = new ArrayList<>(reqStats.values());
        ret.sort((a, b) -> (int) (1000 * (a.getAvg() - b.getAvg())));
        return ret;
    }

    public void addRequest(String scenario, String requestName, long start, long end, boolean success) {
        RequestStat request = reqStats.computeIfAbsent(requestName,
                n -> new RequestStat(simulationName, scenario, n, this.start, apdexT));
        request.add(start, end, success);
        simStat.add(start, end, success);
    }

    public void computeStat() {
        maxUsers = users.values().stream().mapToInt(CountMax::getMax).sum();
        simStat.computeStat(maxUsers);
        reqStats.values()
                .forEach(request -> request.computeStat(simStat.duration(), users.get(request.getScenario()).maximum));
    }

    public void setScenarioName(String name) {
        this.scenarioName = name;
        simStat.setScenario(name);
    }

    public void setStart(long start) {
        this.start = start;
        simStat.setStart(start);
    }

    public SimulationContext setScripts(List<String> scripts) {
        this.scripts = scripts;
        return this;
    }

//    @Override
//    public String toString() {
//        return simStat.toString() + "\n"
//                + getRequests().stream().map(RequestStat::toString).collect(Collectors.joining("\n"));
//    }

    public List<RequestStat> getRequestStats() {
        return getRequests();
    }


    public SimulationContext setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
        return this;
    }

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

    public RequestStat getSimulationStats() {
        return simStat;
    }

    class CountMax {
        int current = 0, maximum = 0;

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
    }

}
