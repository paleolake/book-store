package book.store.service;

import book.store.common.MongoDBManager;
import book.store.common.Result;
import book.store.model.Customer;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class CustomerServiceMongoDBImpl implements CustomerService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public Result<Customer> addCustomer(Customer customer) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("customers");
            customer.setId(MongoDBManager.getNextSequence("customer:id", database));
            customer.setPassword(DigestUtils.sha256Hex(customer.getPassword()));
            Document document = new Document()
                    .append("id", customer.getId())
                    .append("email", customer.getEmail())
                    .append("mobileNo", customer.getMobileNo())
                    .append("password", customer.getPassword())
                    .append("sex", (customer.getSex() != null ? customer.getSex().name() : Customer.Sex.MALE.name()))
                    .append("createTime", customer.getCreateTime())
                    .append("updateTime", customer.getUpdateTime());
            collection.insertOne(document);
            return new Result<>(true, customer);
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
    public Result<Customer> login(String mobileNo, String password) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("customers");
            Document doc = collection.find(and(eq("mobileNo", mobileNo), eq("password", DigestUtils.sha256Hex(password)))).first();
            if (doc != null) {
                Customer customer = new Customer();
                customer.setId(doc.getInteger("id"));
                customer.setEmail(doc.getString("email"));
                customer.setMobileNo(doc.getString("mobileNo"));
                customer.setPassword(doc.getString("password"));
                if (StringUtils.equals(doc.getString("sex"), Customer.Sex.MALE.name())) {
                    customer.setSex(Customer.Sex.MALE);
                } else {
                    customer.setSex(Customer.Sex.FEMALE);
                }
                customer.setCreateTime(doc.getDate("createTime"));
                customer.setUpdateTime(doc.getDate("updateTime"));
                return new Result<>(true, customer);
            }
            return new Result<>(false, "手机号码或密码错误！");
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
    public Result<?> countByMobileNo(String mobileNo) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("customers");
            Document customer = collection.find(eq("mobileNo", mobileNo)).first();
            if (customer != null) {
                return new Result(false);
            }
            return new Result(true);
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
