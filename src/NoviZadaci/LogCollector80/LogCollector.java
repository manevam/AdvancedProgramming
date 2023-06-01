package NoviZadaci.LogCollector80;

import java.util.*;

public class LogCollector {

    Map<String, List<Microservice>> allLogs;

    public LogCollector() {
        allLogs = new HashMap<String , List<Microservice>>();
    }

    public void addLog(String line){
        String [] parts = line.split(" ");
        String serviceName = parts[0];
        String microserviceName = parts[1];
        LogType type = LogType.valueOf(parts[2]);
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i<parts.length-3; i++){
            sb.append(parts[i] + " ");
        }
        sb.append(parts[parts.length-2]);
        String message = sb.toString();
        int timestamp = Integer.parseInt(parts[parts.length-1]);

        Log log = null;
        if(type.equals(LogType.INFO))
            log = new InfoLog(type,message,timestamp);
        if(type.equals(LogType.WARN))
            log = new WarnLog(type,message,timestamp);
        if(type.equals(LogType.ERROR))
            log = new ErrorLog(type,message,timestamp);

        allLogs.putIfAbsent(serviceName, new ArrayList<Microservice>());
        Log finalLog = log;
        allLogs.computeIfPresent(serviceName, (key, value) -> {
            //dali postoi mikroservisot
            Microservice mHere = value.stream().filter(m -> m.microserviceName.equals(microserviceName))
                    .findFirst().orElse(null);
            if(mHere == null) {
                //ne postoi
                Microservice m = new Microservice(microserviceName);
                m.logs.add(finalLog);
                value.add(m);
            } else {
                //postoi
                mHere.logs.add(finalLog);
            }
            return value;
        });
    }

    double getAvgSeverityForService(List<Microservice> mi){
        int sum = mi.stream().mapToInt(m -> m.logs.stream().mapToInt(l -> l.severity).sum()).sum();

        return (double) sum / getTotalNumberOfLogsForService(mi);
    }

    int getTotalNumberOfLogsForService(List<Microservice> m){
        return m.stream().mapToInt(mi -> mi.logs.size()).sum();
    }



    public void printServicesBySeverity(){
        allLogs.entrySet().stream()
                .sorted(Comparator.comparing((Map.Entry<String, List<Microservice>> entry) -> getAvgSeverityForService(entry.getValue())).reversed())
                .forEach(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Service name: %s ", e.getKey()));
            sb.append(String.format("Count of microservices: %d ", e.getValue().size()));
            sb.append(String.format("Total logs in service: %d ", getTotalNumberOfLogsForService(e.getValue())));
            sb.append(String.format("Average severity for all logs: %.2f ", getAvgSeverityForService(e.getValue())));
            sb.append(String.format("Average number of logs per microservice: %.2f", (double) getTotalNumberOfLogsForService(e.getValue()) / e.getValue().size() ));
            System.out.println(sb.toString());
        });
    }

    public Map<Integer, Integer> getSeverityDistribution (String service, String microservice){
        Map<Integer, Integer> result = new HashMap<Integer,Integer>();
        allLogs.computeIfPresent(service, (key, value) -> {
            Microservice m1 = value.stream().filter(m -> m.microserviceName.equals(microservice)).findFirst().orElse(null);
            if(m1 != null){
                m1.logs.stream().forEach(l -> {
                    result.computeIfAbsent(l.severity, key1 -> 0);
                    result.computeIfPresent(l.severity, (key2,value2) -> value2 + 1);
                });
            } else {
                value.stream().forEach(mic -> {
                    mic.logs.stream().forEach(log -> {
                        result.computeIfAbsent(log.severity, key1 -> 0);
                        result.computeIfPresent(log.severity, (key2,value2) -> value2 + 1);
                    });
                });
            }
            return value;
        });

        return result;
    }

    public void displayLogs(String service, String microservice, String order){
        String serviceName = String.valueOf(allLogs.entrySet().stream().filter(e -> e.getKey().equals(service)).findAny().orElse(null));
        StringBuilder sb = new StringBuilder();
        if(microservice != null)
            sb.append(String.format("displayLogs %s %s %s\n", service, microservice,order));
        else
            sb.append(String.format("displayLogs %s %s\n", service, order));

        if(order.equals("NEWEST_FIRST")) {
            allLogs.computeIfPresent(service, (key, value) -> {
                Microservice m1 = value.stream().filter(m -> m.microserviceName.equals(microservice)).findFirst().orElse(null);
                if (m1 != null) {
                    m1.logs.stream().sorted(Comparator.comparing(Log::getTimestamp).reversed()).forEach(l -> {
                        sb.append(String.format("%s|%s ", service, m1.microserviceName));
                        sb.append(l.toString() + "\n");
                    });
                } else {
                    value.stream().forEach(mic -> {
                        mic.logs.stream().forEach(log -> {
                            mic.logs.stream().sorted(Comparator.comparing(Log::getTimestamp).reversed()).forEach(l -> {
                                sb.append(String.format("%s|%s ", service, mic.microserviceName));
                                sb.append(l.toString() + "\n");
                            });
                        });
                    });
                }
                return value;
            });
            System.out.println(sb.toString());
        }
         else if(order.equals("OLDEST_FIRST")) {

            allLogs.computeIfPresent(service, (key, value) -> {
                Microservice m1 = value.stream().filter(m -> m.microserviceName.equals(microservice)).findFirst().orElse(null);
                if (m1 != null) {
                    m1.logs.stream().sorted(Comparator.comparing(Log::getTimestamp)).forEach(l -> {
                        sb.append(String.format("%s|%s ", service, m1.microserviceName));
                        sb.append(l.toString() + "\n");
                    });
                } else {
                    value.stream().forEach(mic -> {
                        mic.logs.stream().forEach(log -> {
                            mic.logs.stream().sorted(Comparator.comparing(Log::getTimestamp)).forEach(l -> {
                                sb.append(String.format("%s|%s ", service, mic.microserviceName));
                                sb.append(l.toString() + "\n");
                            });
                        });
                    });
                }
                return value;
            });
            System.out.println(sb.toString());
        }

        else if(order.equals("MOST_SEVERE_FIRST")) {

            allLogs.computeIfPresent(service, (key, value) -> {
                Microservice m1 = value.stream().filter(m -> m.microserviceName.equals(microservice)).findFirst().orElse(null);
                if (m1 != null) {
                    m1.logs.stream().sorted(Comparator.comparing(Log::getSeverity).reversed()).forEach(l -> {
                        sb.append(String.format("%s|%s ", service, m1.microserviceName));
                        sb.append(l.toString() + "\n");
                    });
                } else {
                    value.stream().forEach(mic -> {
                        mic.logs.stream().forEach(log -> {
                            mic.logs.stream().sorted(Comparator.comparing(Log::getSeverity).reversed()).forEach(l -> {
                                sb.append(String.format("%s|%s ", service, mic.microserviceName));
                                sb.append(l.toString() + "\n");
                            });
                        });
                    });
                }
                return value;
            });
            System.out.println(sb.toString());
        }
        else if(order.equals("LEAST_SEVERE_FIRST")) {

            allLogs.computeIfPresent(service, (key, value) -> {
                Microservice m1 = value.stream().filter(m -> m.microserviceName.equals(microservice)).findFirst().orElse(null);
                if (m1 != null) {
                    m1.logs.stream().sorted(Comparator.comparing(Log::getSeverity)).forEach(l -> {
                        sb.append(String.format("%s|%s ", service, m1.microserviceName));
                        sb.append(l.toString() + "\n");
                    });
                } else {
                    value.stream().forEach(mic -> {
                        mic.logs.stream().forEach(log -> {
                            mic.logs.stream().sorted(Comparator.comparing(Log::getSeverity)).forEach(l -> {
                                sb.append(String.format("%s|%s ", service, mic.microserviceName));
                                sb.append(l.toString() + "\n");
                            });
                        });
                    });
                }
                return value;
            });
            System.out.println(sb.toString());
        }

    }
}
