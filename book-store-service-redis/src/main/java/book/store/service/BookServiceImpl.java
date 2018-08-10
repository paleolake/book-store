package book.store.service;

import book.store.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl implements BookService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public List<Book> queryBooks(Integer beginNum, Integer pageSize) {
        try {
//            return runner.query("SELECT * FROM book LIMIT ?,?;",
//                    new BeanListHandler<>(Book.class, rowProcessor), new Object[]{beginNum, pageSize});
            return null;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Book findById(Integer bookId) {
        return null;
    }
}
