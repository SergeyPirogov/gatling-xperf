# gatling-xperf

Analyze Gatling simulation.log and generate csv report

```
./xperf -f simulation.log 
```

Analyze base simulation and challenger 

```
./xperf -f simulation.log -c simulation2.log
```

Compare results with daff

```
daff --output diff.html 1678000685174_simulation.csv 1678000685192_simulation2.csv
```

Run with docker:

```
docker run -v ${PWD}/data:/data spirogov/xperf --file="/data/simulation.log"
```

Compare two runs:

```
docker run -v ${PWD}/data:/data spirogov/xperf --file="/data/simulation.log" --challenger="/data/simulation2.log"
```