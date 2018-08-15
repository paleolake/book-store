package book.store.service;

import book.store.common.MongoDBManager;
import book.store.common.Result;
import book.store.model.Category;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceMongoDBImpl implements CategoryService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public Result<?> addCategory(Category category) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("categories");
            Document document = new Document()
                    .append("id", category.getId())
                    .append("parentId", category.getParentId())
                    .append("categoryName", category.getCategoryName())
                    .append("createTime", category.getCreateTime())
                    .append("updateTime", category.getUpdateTime());
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

    @Override
    public List<Category> queryCategories() {
        MongoClient mongoClient = null;
        MongoCursor<Document> cursor = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("categories");
            cursor = collection.find().iterator();
            List<Category> categories = new ArrayList<>();
            while (cursor.hasNext()) {
                Category category = new Category();
                Document doc = cursor.next();
                category.setId(doc.getInteger("id"));
                category.setParentId(doc.getInteger("parentId"));
                category.setCategoryName(doc.getString("categoryName"));
                category.setCreateTime(doc.getDate("createTime"));
                category.setUpdateTime(doc.getDate("updateTime"));
                categories.add(category);
            }
            return categories;
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
}
