package NoviZadaci.LogCollector80;

public class InfoLog extends Log{
    LogType type;

    public InfoLog(LogType type, String message, int timestamp) {
        super(message, timestamp);
        this.type = type;
        this.severity = 0;
    }

    @Override
    public String toString() {
        return String.format("Type: %s, Message: %s, Timestamp: %d, Severity: %d",
                type.name(), message, timestamp,severity);
    }

}
