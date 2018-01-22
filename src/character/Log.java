package character;

import util.Queue;

public class Log {
    private static final int LOG_SIZE = 8;
    private Queue<String> log;

    public Log() {
        log = new Queue<>(LOG_SIZE);
    }

    void add(String logString) {
        log.add(logString);
    }

    public Iterable<String> getLogQueue() {
        return log;
    }
}