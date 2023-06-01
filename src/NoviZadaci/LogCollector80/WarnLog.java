package NoviZadaci.LogCollector80;

public class WarnLog extends Log{

    LogType type;

    public WarnLog(LogType type, String message, int timestamp) {
        super(message, timestamp);
        this.type = type;
        this.severity = 1;
        checkKeyWords();
    }

    void checkKeyWords(){
        if(message.contains("error"))
            this.severity +=1;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %d T:%d",
                type.name(), message, timestamp, timestamp);
    }

}
