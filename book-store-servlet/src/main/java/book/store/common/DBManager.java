package book.store.common;

import book.store.util.ConfigUtils;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DataSource dataSource;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(ConfigUtils.getProperty(Constants.JDBC_DRIVER_CLASS_NAME));
        Connection conn = DriverManager.getConnection(ConfigUtils.getProperty(Constants.JDBC_URL),
                ConfigUtils.getProperty(Constants.JDBC_USERNAME), ConfigUtils.getProperty(Constants.JDBC_PASSWORD));
        if (conn == null) {
            throw new RuntimeException("Not connect to the database");
        }
        return conn;
    }


    public synchronized static DataSource getDataSource() {
        if (dataSource == null) {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName(ConfigUtils.getProperty(Constants.JDBC_DRIVER_CLASS_NAME));
            ds.setUrl(ConfigUtils.getProperty(Constants.JDBC_URL));
            ds.setUsername(ConfigUtils.getProperty(Constants.JDBC_USERNAME));
            ds.setPassword(ConfigUtils.getProperty(Constants.JDBC_PASSWORD));
            ds.setInitialSize(1);
            ds.setMaxIdle(2);
            dataSource = ds;
        }
        return dataSource;
    }

}
