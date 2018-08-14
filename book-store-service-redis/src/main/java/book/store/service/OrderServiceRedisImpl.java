package book.store.service;

import book.store.common.JedisManager;
import book.store.common.Result;
import book.store.model.Book;
import book.store.model.OrderDetail;
import book.store.model.OrderInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceRedisImpl implements OrderService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private final String KEY_ORDER_ID_CREATE = "order:orderId";//用于订单ID的生成
    private final String KEY_ORDER_DETAIL_ID_CREATE = "order:detailId";//用于订单明细ID的生成
    private final String KEY_ORDER = "order:%d";//订单信息的HASH KEY.
    private final String KEY_ORDER_IDS = "order:orderIds";//订单ID集的LIST KEY.
    private final String KEY_ORDER_DETAIL = "order:detail:%d";//订单详细信息的HASH KEY
    private final String FIELD_ORDER_DETAIL_IDS = "order:detailIds";//订单详细信息的HASH KEY


    @Override
    public Result<?> addOrder(OrderInfo orderInfo) {
        try (Jedis jedis = JedisManager.getJedis()) {
            orderInfo.setId(jedis.incr(KEY_ORDER_ID_CREATE).intValue());
            for (OrderDetail detail : orderInfo.getOrderDetails()) {
                detail.setId(jedis.incr(KEY_ORDER_DETAIL_ID_CREATE).intValue());
            }
            String orderKey = String.format(KEY_ORDER, orderInfo.getId());
            Transaction trans = jedis.multi();
            trans.hset(orderKey, "id", orderInfo.getId() + "");
            trans.hset(orderKey, "customerId", orderInfo.getCustomerId() + "");
            trans.hset(orderKey, "orderCode", orderInfo.getOrderCode());
            trans.hset(orderKey, "orderAmt", orderInfo.getOrderAmt() + "");
            trans.hset(orderKey, "orderState", orderInfo.getOrderState().name());
            trans.hset(orderKey, "createTime", orderInfo.getCreateTime().getTime() + "");
            trans.hset(orderKey, "updateTime", orderInfo.getUpdateTime().getTime() + "");

            StringBuffer detailIds = new StringBuffer();
            for (OrderDetail detail : orderInfo.getOrderDetails()) {
                String detailKey = String.format(KEY_ORDER_DETAIL, detail.getId());
                trans.hset(detailKey, "id", detail.getId() + "");
                trans.hset(detailKey, "orderId", orderInfo.getId() + "");
                trans.hset(detailKey, "bookId", detail.getBookId() + "");
                trans.hset(detailKey, "bookAmt", detail.getBookAmt() + "");
                trans.hset(detailKey, "bookCount", detail.getBookCount() + "");
                trans.hset(detailKey, "updateTime", detail.getUpdateTime().getTime() + "");

                detailIds.append(":" + detail.getId());
            }
            trans.hset(orderKey, FIELD_ORDER_DETAIL_IDS, detailIds.substring(1));//将订单明细ID保存到订单hashes中。
            trans.rpush(KEY_ORDER_IDS, orderInfo.getId() + "");
            trans.exec();
            return new Result(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        }
    }

    @Override
    public List<OrderInfo> queryOrders(Integer beginIndex, Integer pageSize) {
        try (Jedis jedis = JedisManager.getJedis()) {
            List<String> orderIds = jedis.lrange(KEY_ORDER_IDS, beginIndex, (beginIndex + pageSize));
            List<OrderInfo> orders = new ArrayList<>(orderIds.size());
            for (String orderId : orderIds) {
                OrderInfo orderInfo = findById(Integer.parseInt(orderId));
                orders.add(orderInfo);
            }
            return orders;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public OrderInfo findById(Integer orderId) {
        try (Jedis jedis = JedisManager.getJedis()) {
            OrderInfo orderInfo = new OrderInfo();
            String hashKey = String.format(KEY_ORDER, orderId);
            orderInfo.setId(Integer.parseInt(jedis.hget(hashKey, "id")));
            orderInfo.setCustomerId(Integer.parseInt(jedis.hget(hashKey, "customerId")));
            orderInfo.setOrderCode(jedis.hget(hashKey, "orderCode"));
            orderInfo.setOrderAmt(Double.parseDouble(jedis.hget(hashKey, "orderAmt")));
            String orderState = jedis.hget(hashKey, "orderState");
            if (StringUtils.equals(OrderInfo.OrderState.NONPAID.name(), orderState)) {
                orderInfo.setOrderState(OrderInfo.OrderState.NONPAID);
            } else if (StringUtils.equals(OrderInfo.OrderState.PAID.name(), orderState)) {
                orderInfo.setOrderState(OrderInfo.OrderState.PAID);
            } else {
                orderInfo.setOrderState(OrderInfo.OrderState.CANCELLED);
            }
            orderInfo.setCreateTime(new Date(Long.parseLong(jedis.hget(hashKey, "createTime"))));
            orderInfo.setUpdateTime(new Date(Long.parseLong(jedis.hget(hashKey, "updateTime"))));

            String[] detailIds = StringUtils.split(jedis.hget(hashKey, FIELD_ORDER_DETAIL_IDS), ":");
            for (String detailId : detailIds) {
                String detailKey = String.format(KEY_ORDER_DETAIL, Integer.parseInt(detailId));
                OrderDetail detail = new OrderDetail();
                detail.setId(Integer.parseInt(jedis.hget(detailKey, "id")));
                detail.setOrderId(Integer.parseInt(jedis.hget(detailKey, "orderId")));
                detail.setBookId(Integer.parseInt(jedis.hget(detailKey, "bookId")));
                detail.setBookAmt(Double.parseDouble(jedis.hget(detailKey, "bookAmt")));
                detail.setBookCount(Integer.parseInt(jedis.hget(detailKey, "bookCount")));
                detail.setUpdateTime(new Date(Long.parseLong(jedis.hget(hashKey, "updateTime"))));
                Book book = DefaultServiceFactory.getInstance().createService(BookService.class).findById(detail.getBookId());
                detail.setBookName(book.getBookName());
                orderInfo.getOrderDetails().add(detail);
            }
            return orderInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
