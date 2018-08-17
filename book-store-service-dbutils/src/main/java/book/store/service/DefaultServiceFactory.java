package book.store.service;

import java.util.HashMap;
import java.util.Map;

public class DefaultServiceFactory implements ServiceFactory {
    private static Map<String, Object> instance;

    private DefaultServiceFactory() {
    }

    static {
        instance = new HashMap<>();
        instance.put("BookService", new BookServiceJdbcImpl());
        instance.put("CustomerService", new CustomerServiceJdbcImpl());
        instance.put("OrderService", new OrderServiceJdbcImpl());
        instance.put("CategoryService", new CategoryServiceJdbcImpl());
        instance.put("SystemService", new DBInitManager());
        instance.put("serviceFactory", new DefaultServiceFactory());
    }


    @Override
    public <T> T createService(Class<T> clazz) {
        return (T) instance.get(clazz.getSimpleName());
    }

    public static ServiceFactory getInstance() {
        return (ServiceFactory) instance.get("serviceFactory");
    }
}
