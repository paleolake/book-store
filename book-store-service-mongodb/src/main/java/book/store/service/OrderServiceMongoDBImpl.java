package book.store.service;

import book.store.common.MongoDBManager;
import book.store.common.Result;
import book.store.model.Book;
import book.store.model.OrderDetail;
import book.store.model.OrderInfo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class OrderServiceMongoDBImpl implements OrderService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public Result<?> addOrder(OrderInfo orderInfo) {
        MongoClient mongoClient = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("orders");
            Document orderDoc = new Document()
                    .append("id", orderInfo.getId())
                    .append("customerId", orderInfo.getCustomerId())
                    .append("orderCode", orderInfo.getOrderCode())
                    .append("orderAmt", orderInfo.getOrderAmt())
                    .append("orderState", orderInfo.getOrderState().name())
                    .append("createTime", orderInfo.getCreateTime())
                    .append("updateTime", orderInfo.getUpdateTime());
            List<Document> orderDetails = new ArrayList<>(orderInfo.getOrderDetails().size());
            for (OrderDetail detail : orderInfo.getOrderDetails()) {
                Document doc = new Document()
                        .append("id", detail.getId())
                        .append("orderId", detail.getOrderId())
                        .append("bookId", detail.getBookId())
                        .append("bookAmt", detail.getBookAmt())
                        .append("bookCount", detail.getBookCount())
                        .append("updateTime", orderInfo.getUpdateTime());
                orderDetails.add(doc);
            }
            orderDoc.append("orderDetails", orderDetails);
            collection.insertOne(orderDoc);
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
    public List<OrderInfo> queryOrders(Integer customerId, Integer beginIndex, Integer pageSize, Object... params) {
        MongoClient mongoClient = null;
        MongoCursor<Document> cursor = null;
        try {
            mongoClient = MongoDBManager.getMongoClient();
            MongoDatabase database = mongoClient.getDatabase(MongoDBManager.DEFAULT_DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection("orders");
            cursor = collection.find(eq("customerId", customerId)).skip(beginIndex).limit(pageSize).iterator();
            List<OrderInfo> orders = new ArrayList<>();
            while (cursor.hasNext()) {
                orders.add(convertToOrders(cursor.next()));
            }
            return orders;
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

    private OrderInfo convertToOrders(Document doc) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(doc.getInteger("id"));
        orderInfo.setCustomerId(doc.getInteger("customerId"));
        orderInfo.setOrderCode(doc.getString("orderCode"));
        orderInfo.setOrderAmt(doc.getDouble("orderAmt"));
        if (StringUtils.equals(doc.getString("orderState"), OrderInfo.OrderState.NONPAID.name())) {
            orderInfo.setOrderState(OrderInfo.OrderState.NONPAID);
        } else if (StringUtils.equals(doc.getString("orderState"), OrderInfo.OrderState.PAID.name())) {
            orderInfo.setOrderState(OrderInfo.OrderState.PAID);
        } else {
            orderInfo.setOrderState(OrderInfo.OrderState.CANCELLED);
        }
        orderInfo.setCreateTime(doc.getDate("createTime"));
        orderInfo.setUpdateTime(doc.getDate("updateTime"));

        List<Document> orderDetails = (List<Document>) doc.getOrDefault("orderDetails", null);
        if (orderDetails == null || orderDetails.size() <= 0) {
            throw new RuntimeException("订单明细数据不存在！");
        }
        for (Document detail : orderDetails) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId(detail.getInteger("id"));
            orderDetail.setOrderId(detail.getInteger("orderId"));
            orderDetail.setBookId(detail.getInteger("bookId"));
            orderDetail.setBookAmt(detail.getDouble("bookAmt"));
            orderDetail.setBookCount(detail.getInteger("bookCount"));

            orderDetail.setUpdateTime(detail.getDate("updateTime"));
            Book book = DefaultServiceFactory.getInstance().createService(BookService.class).findById(orderDetail.getBookId());
            orderDetail.setBookName(book.getBookName());
            orderInfo.getOrderDetails().add(orderDetail);
        }
        return orderInfo;
    }
}
