public class Logger {
    private enum Mode {
        DEBUG,
        INFO,
        ERROR
    }
    private static Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private Logger() {}

    private void log (Mode m, String fmt, Object... args) {
        System.out.println("[" + m.name() + "] " + String.format(fmt, args));
    }

    public void info (String fmt, Object... args) {
        log(Mode.INFO, fmt, args);
    }

    public void error (String fmt, Object... args) {
        log(Mode.ERROR, fmt, args);
    }

    public void debug (String fmt, Object... args) {
        log(Mode.DEBUG, fmt, args);
    }
}
