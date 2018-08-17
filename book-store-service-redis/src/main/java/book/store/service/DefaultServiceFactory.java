package book.store.service;

import java.util.HashMap;
import java.util.Map;

public class DefaultServiceFactory implements ServiceFactory {
    private static Map<String, Object> instance;

    private DefaultServiceFactory() {
    }

    static {
        instance = new HashMap<>();
        instance.put("BookService", new BookServiceRedisImpl());
        instance.put("CustomerService", new CustomerServiceRedisImpl());
        instance.put("OrderService", new OrderServiceRedisImpl());
        instance.put("CategoryService", new CategoryServiceRedisImpl());
        instance.put("SystemService", new RedisInitManager());
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
