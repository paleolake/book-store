package book.store.service;

import book.store.common.DBManager;
import book.store.common.Result;
import book.store.model.Customer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CustomerService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private static CustomerService customerService;
    private QueryRunner runner;
    private BasicRowProcessor rowProcessor;

    private CustomerService() {
        runner = new QueryRunner(DBManager.getDataSource());
        rowProcessor = new BasicRowProcessor(new GenerousBeanProcessor());
    }

    public static synchronized CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public Result<Customer> addCustomer(Customer customer) {
        try {
            customer.setNickname(String.format("%s_%s", StringUtils.isBlank(customer.getEmail()) ? StringUtils.substring(customer.getEmail(), 0, 4) : StringUtils.substring(customer.getMobileNo(), 0, 4), RandomStringUtils.randomAlphabetic(10)));
            customer.setPassword(DigestUtils.sha256Hex(customer.getPassword()));
            customer.setSex(customer.getSex() == null ? Customer.Sex.MALE : customer.getSex());
            customer.setCreateTime(new Date());
            customer.setUpdateTime(new Date());
            String sql = "INSERT INTO customer(nickname,mobile_no,PASSWORD,email,sex,create_time,update_time)VALUES(?,?,?,?,?,?,?);";
            Integer customerId = runner.insert(sql, new ScalarHandler<>(),
                    new Object[]{customer.getNickname(), customer.getMobileNo(), customer.getPassword(), customer.getEmail(), customer.getSex()});
            customer.setId(customerId);
            return new Result(true, customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }

    public Result<Customer> login(String mobileNo, String password) {
        try {
            Customer customer = runner.query("select * from customer where mobile_no = ? and password = ? limit 1;", new BeanHandler<>(Customer.class, rowProcessor), mobileNo, DigestUtils.sha256Hex(password));
            return new Result(true, customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }
}
