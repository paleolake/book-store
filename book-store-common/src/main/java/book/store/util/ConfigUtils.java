package book.store.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
    private static final Logger logger = LogManager.getLogger(ConfigUtils.class.getCanonicalName());
    private static Properties properties;

    /**
     * 获取属性值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static synchronized String getProperty(String key, String defaultValue, String fileName) {
        try {
            if (properties == null) {
                properties = loadProperties(fileName);
            }
            return properties.getProperty(key, defaultValue);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 获取属性值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static synchronized String getProperty(String key, String defaultValue) {
        return getProperty(key, defaultValue, "jdbc.properties");
    }

    /**
     * 获取属性值
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        return getProperty(key, null, "jdbc.properties");
    }


    /**
     * 加载属性文件
     *
     * @param fileName
     * @return
     */
    public static Properties loadProperties(String fileName) {
        try {
            Properties p = new Properties();
            File configFile = new File(System.getProperty("user.dir") + File.separatorChar + fileName);
            InputStream input;
            if (configFile.exists()) {
                input = new FileInputStream(configFile);
            } else {
                ClassLoader classLoader = ConfigUtils.class.getClassLoader();
                input = classLoader.getResourceAsStream(fileName);
            }
            p.load(input);
            return p;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
