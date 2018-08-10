package book.store.service;

import book.store.common.DBManager;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;

public abstract class BaseService {
    protected QueryRunner runner = new QueryRunner(DBManager.getDataSource());
    protected BasicRowProcessor rowProcessor = new BasicRowProcessor(new GenerousBeanProcessor());

}
