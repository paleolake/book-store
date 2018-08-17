package book.store.listener;

import book.store.service.DefaultServiceFactory;
import book.store.service.ServiceFactory;
import book.store.service.SystemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SystemInitListener implements ServletContextListener {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ServiceFactory serviceFactory = DefaultServiceFactory.getInstance();
            serviceFactory.createService(SystemService.class).init();
        } catch (Exception e) {
            logger.error("系统初始化失败，" + e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
