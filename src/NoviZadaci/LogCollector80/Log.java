package NoviZadaci.LogCollector80;

public class Log {
    String message;
    int timestamp;
    int severity;

    public Log(String message, int timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return "Override purposes";
    }
}
