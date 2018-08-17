package book.store.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBInitManager implements SystemService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public void init() {
        try {
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
