package book.store.service;

import book.store.common.MongoDBManager;
import book.store.common.Result;
import book.store.model.Book;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class BookServiceMongoDBImpl implements BookService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private final String COLLECTION_NAME = "books";

    @Override
    public List<Book> queryBooks(Integer beginNum, Integer pageSize) {
        MongoClient mongoClient = null;
        MongoCursor<Document> cursor = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            cursor = collection.find().skip(beginNum).limit(pageSize).iterator();
            List<Book> books = new ArrayList<>();
            while (cursor.hasNext()) {
                books.add(convertToBook(cursor.next()));
            }
            return books;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public Book findById(Integer bookId) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Document doc = collection.find(eq("id", bookId)).first();
            return convertToBook(doc);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    private Book convertToBook(Document doc) {
        Book book = new Book();
        book.setId(doc.getInteger("id"));
        book.setCategoryId(doc.getInteger("categoryId"));
        book.setBookName(doc.getString("bookName"));
        book.setAuthor(doc.getString("author"));
        book.setPublisherName(doc.getString("publisherName"));
        book.setPrice(doc.getDouble("price"));
        book.setPublishDate(doc.getDate("publishDate"));
        book.setCreateTime(doc.getDate("createTime"));
        book.setUpdateTime(doc.getDate("createTime"));
        return book;
    }

    @Override
    public Result<?> addBook(Book book) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
            Document document = new Document()
                    .append("id", book.getId())
                    .append("categoryId", book.getCategoryId())
                    .append("bookName", book.getBookName())
                    .append("author", book.getAuthor())
                    .append("publisherName", book.getPublisherName())
                    .append("price", book.getPrice())
                    .append("publishDate", book.getPublishDate())
                    .append("createTime", book.getCreateTime())
                    .append("updateTime", book.getUpdateTime());
            collection.insertOne(document);
            return new Result<>(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

}
