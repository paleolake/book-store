package book.store.service;

import book.store.common.JedisManager;
import book.store.common.Result;
import book.store.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 使用list保存图书信息的ID，使用Hashes保存单条图书记录信息
 */
public class BookServiceRedisImpl implements BookService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private final String KEY_BOOK = "book:%d";
    private final String KEY_BOOK_IDS = "book:ids";

    @Override
    public List<Book> queryBooks(Integer beginNum, Integer pageSize, Object... params) {
        try (Jedis jedis = JedisManager.getJedis()) {
            List<String> bookIds = jedis.lrange(KEY_BOOK_IDS, beginNum, (beginNum + pageSize));
            List<Book> books = new ArrayList<>(bookIds.size());
            for (String bookId : bookIds) {
                Book book = findById(Integer.parseInt(bookId));
                books.add(book);
            }
            return books;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Book findById(Integer bookId) {
        try (Jedis jedis = JedisManager.getJedis()) {
            String hashKey = String.format(KEY_BOOK, bookId);
            Book book = new Book();
            book.setId(Integer.parseInt(jedis.hget(hashKey, "id")));
            book.setCategoryId(Integer.parseInt(jedis.hget(hashKey, "categoryId")));
            book.setBookName(jedis.hget(hashKey, "bookName"));
            book.setAuthor(jedis.hget(hashKey, "author"));
            book.setPublisherName(jedis.hget(hashKey, "publisherName"));
            book.setPrice(Double.parseDouble(jedis.hget(hashKey, "price")));
            book.setPublishDate(new Date(Long.parseLong(jedis.hget(hashKey, "publishDate"))));
            book.setCreateTime(new Date(Long.parseLong(jedis.hget(hashKey, "createTime"))));
            book.setUpdateTime(new Date(Long.parseLong(jedis.hget(hashKey, "updateTime"))));
            return book;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public Result<?> addBook(Book book) {
        try (Jedis jedis = JedisManager.getJedis()) {
            String hashKey = String.format(KEY_BOOK, book.getId());
            Transaction trans = jedis.multi();
            trans.hset(hashKey, "id", book.getId() + "");
            trans.hset(hashKey, "categoryId", book.getCategoryId() + "");
            trans.hset(hashKey, "bookName", book.getBookName());
            trans.hset(hashKey, "author", book.getAuthor() + "");
            trans.hset(hashKey, "publisherName", book.getPublisherName());
            trans.hset(hashKey, "price", book.getPrice() + "");
            trans.hset(hashKey, "publishDate", book.getPublishDate().getTime() + "");
            trans.hset(hashKey, "createTime", book.getCreateTime().getTime() + "");
            trans.hset(hashKey, "updateTime", book.getUpdateTime().getTime() + "");
            trans.rpush(KEY_BOOK_IDS, book.getId() + "");
            trans.exec();
            return new Result(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        }
    }
}
