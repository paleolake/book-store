package book.store.service;

import book.store.common.Result;
import book.store.model.Customer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CustomerServiceImpl extends BaseService implements CustomerService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    public Result<Customer> addCustomer(Customer customer) {
        try {
            customer.setPassword(DigestUtils.sha256Hex(customer.getPassword()));
            customer.setSex(customer.getSex() == null ? Customer.Sex.MALE : customer.getSex());
            customer.setEmail("");
            customer.setCreateTime(new Date());
            customer.setUpdateTime(new Date());
            String sql = "INSERT INTO customer(mobile_no,PASSWORD,email,sex,create_time,update_time)VALUES(?,?,?,?,NOW(),NOW());";
            Long customerId = runner.insert(sql, new ScalarHandler<>(),
                    new Object[]{customer.getMobileNo(), customer.getPassword(), customer.getEmail(), customer.getSex().name()});
            customer.setId(customerId.intValue());
            return new Result(true, customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }

    public Result<Customer> login(String mobileNo, String password) {
        try {
            Customer customer = runner.query("select * from customer where mobile_no = ? and password = ? limit 1;", new BeanHandler<>(Customer.class, rowProcessor), mobileNo, DigestUtils.sha256Hex(password));
            if (customer == null) {
                return new Result(false, "手机号码或密码错误！");
            }
            return new Result(true, customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }

    public Result<?> countByMobileNo(String mobileNo) {
        try {
            long count = runner.query("select count(*) from customer where mobile_no = ?", new ScalarHandler<>(), mobileNo);
            if (count > 0) {
                return new Result(false);
            }
            return new Result(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }
}
