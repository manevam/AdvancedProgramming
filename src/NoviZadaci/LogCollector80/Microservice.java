package NoviZadaci.LogCollector80;

import java.util.ArrayList;
import java.util.List;

public class Microservice {
    String microserviceName;
    List<Log> logs;

    public Microservice(String microserviceName) {
        this.microserviceName = microserviceName;
        this.logs = new ArrayList<Log>();
    }

    public int numberOfLogs(){
        return logs.size();
    }

}
