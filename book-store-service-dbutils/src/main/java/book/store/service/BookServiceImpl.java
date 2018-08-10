package book.store.service;

import book.store.model.Book;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl extends BaseService implements BookService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    public List<Book> queryBooks(Integer beginNum, Integer pageSize) {
        try {
            return runner.query("SELECT * FROM book LIMIT ?,?;",
                    new BeanListHandler<>(Book.class, rowProcessor), new Object[]{beginNum, pageSize});
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    public Book findById(Integer bookId) {
        try {
            return runner.query("select * from book where id = ? limit 1;",
                    new BeanHandler<>(Book.class, rowProcessor), bookId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
