package book.store.service;

import book.store.common.DBManager;
import book.store.common.Result;
import book.store.model.OrderDetail;
import book.store.model.OrderInfo;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceJdbcImpl extends BaseService implements OrderService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    public Result<?> addOrder(OrderInfo orderInfo) {
        Connection conn = null;
        try {
            conn = DBManager.getConnection();
            conn.setAutoCommit(false);
            Long orderId = runner.insert("INSERT INTO order_info(customer_id,order_code,order_amt,order_state,create_time,update_time)VALUES(?,?,?,?,NOW(),NOW());",
                    new ScalarHandler<>(), new Object[]{orderInfo.getCustomerId(), orderInfo.getOrderCode(), orderInfo.getOrderAmt(), orderInfo.getOrderState().name()});
            Object[][] params = new Object[orderInfo.getOrderDetails().size()][4];
            for (int i = 0; i < orderInfo.getOrderDetails().size(); i++) {
                OrderDetail detail = orderInfo.getOrderDetails().get(i);
                params[i][0] = orderId;
                params[i][1] = detail.getBookId();
                params[i][2] = detail.getBookAmt();
                params[i][3] = detail.getBookCount();
            }
            runner.batch(conn, "INSERT INTO order_detail(order_id,book_id,book_amt,book_count,update_time)VALUES(?,?,?,?,NOW());", params);
            conn.commit();
            return new Result(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            try {
                conn.rollback();
            } catch (SQLException e1) {
                logger.error(e.getMessage(), e);
            }
            return new Result(false, "程序异常！");
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public List<OrderInfo> queryOrders(Integer customerId, Integer beginIndex, Integer pageSize, Object... params) {
        try {
            String sql = "select * from order_info where customer_id = ? limit ?,?;";
            List<OrderInfo> orders = runner.query(sql, new BeanListHandler<>(OrderInfo.class, rowProcessor), new Object[]{customerId, beginIndex, pageSize});
            StringBuffer symbols = new StringBuffer();
            Integer[] orderIds = new Integer[orders.size()];
            for (int i = 0; i < orderIds.length; i++) {
                orderIds[i] = orders.get(i).getId();
                symbols.append(",?");
            }
            sql = String.format("SELECT t1.*,t2.book_name FROM order_detail t1 LEFT JOIN book t2 ON t2.id = t1.book_id WHERE t1.order_id IN(%s)", symbols.substring(1));
            List<OrderDetail> orderDetails = runner.query(sql, new BeanListHandler<>(OrderDetail.class, rowProcessor), orderIds);
            for (OrderDetail detail : orderDetails) {
                for (OrderInfo orderInfo : orders) {
                    if (orderInfo.getId().intValue() == detail.getOrderId().intValue()) {
                        orderInfo.getOrderDetails().add(detail);
                        break;
                    }
                }
            }
            return orders;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
