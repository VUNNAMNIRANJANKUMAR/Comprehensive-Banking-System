package singleton;

import java.util.logging.Logger;

public class BankingLogger {

    private static final BankingLogger INSTANCE = new BankingLogger();

    private final Logger logger =
            Logger.getLogger(BankingLogger.class.getName());

    private BankingLogger() {
    }

    public static BankingLogger getInstance() {
        return INSTANCE;
    }

    public void info(String message) {
        logger.info(message);
    }
}