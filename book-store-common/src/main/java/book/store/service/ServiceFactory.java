package book.store.service;

public interface ServiceFactory {
    /**
     * 创建服务类的实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T createService(Class<T> clazz);

}
