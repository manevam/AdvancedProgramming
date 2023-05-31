package NoviZadaci.LogCollector80;

public class ErrorLog extends Log{
    LogType type;

    public ErrorLog(LogType type, String message, int timestamp) {
        super(message, timestamp);
        this.type = type;
        this.severity = 3;
        checkKeyWords();
    }

    void checkKeyWords(){
        if(message.contains("fatal"))
            this.severity +=2;
        if(message.contains("exception"))
            this.severity +=3;
    }

    @Override
    public String toString() {
        return String.format("Type: %s, Message: %s, Timestamp: %d, Severity: %d",
                type.name(), message, timestamp,severity);
    }

}
