package book.store.common;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

public class MongoDBManager {
    private final static Logger logger = LogManager.getLogger(MongoDBManager.class.getCanonicalName());
    public final static String DEFAULT_DATABASE_NAME = "book_store";
    public final static String SEQUENCE_COLLECTION = "SEQUENCE_COLLECTION";

    public static MongoClient getMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    /**
     * 获取下一个序列号
     *
     * @param name
     * @param database
     * @return
     */
    public static Integer getNextSequence(String name, MongoDatabase database) {
        MongoCollection<Document> counters = database.getCollection(SEQUENCE_COLLECTION);
        if (counters.countDocuments() == 0) {
            createCountersCollection(counters, name);
        }
        Document searchQuery = new Document("_id", name);
        Document increase = new Document("seq", 1);
        Document updateQuery = new Document("$inc", increase);
        Document result = counters.findOneAndUpdate(searchQuery, updateQuery);
        return result.getInteger("seq");
    }

    /**
     * 创建自增的 Collection
     *
     * @param counters
     * @param name
     */
    private static void createCountersCollection(MongoCollection counters, String name) {
        Document document = new Document();
        document.append("_id", name);
        document.append("seq", 1);
        counters.insertOne(document);
    }

    /**
     * 删除数据库
     *
     * @param databaseName
     * @return
     */
    public static Result<?> dropDatabase(String databaseName) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME).drop();
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
