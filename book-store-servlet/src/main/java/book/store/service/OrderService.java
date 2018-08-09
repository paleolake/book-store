package book.store.service;

import book.store.common.DBManager;
import book.store.common.Result;
import book.store.model.OrderDetail;
import book.store.model.OrderInfo;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private static OrderService orderService;
    private QueryRunner runner;
    private BasicRowProcessor rowProcessor;

    private OrderService() {
        runner = new QueryRunner(DBManager.getDataSource());
        rowProcessor = new BasicRowProcessor(new GenerousBeanProcessor());
    }

    public static synchronized OrderService getInstance() {
        if (orderService == null) {
            orderService = new OrderService();
        }
        return orderService;
    }


    public Result<?> addOrder(OrderInfo orderInfo) {
        Connection conn = null;
        try {
            conn.setAutoCommit(false);
            Integer orderId = runner.insert("INSERT INTO order_info(order_code,order_amt,order_state,create_time,update_time)VALUES(?,?,?,NOW(),NOW());",
                    new ScalarHandler<>(), new Object[]{orderInfo.getOrderCode(), orderInfo.getOrderAmt(), orderInfo.getOrderState()});
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

    public List<OrderInfo> queryOrders(Integer beginIndex, Integer pageSize) {
        try {
            String sql = "";
            return runner.query(sql, new BeanListHandler<>(OrderInfo.class, rowProcessor), new Object[]{beginIndex, pageSize});
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
