package book.store.service;

import book.store.common.JedisManager;
import book.store.common.Result;
import book.store.model.Customer;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;

/**
 * 使用redis中hashes保存顾客的字段信息，使用redis中的list保存顾客id记录，
 * 为了便于快递登录，使用redis中hashes保存以手机号码和密码为键，顾客id为值的记录。
 */
public class CustomerServiceRedisImpl implements CustomerService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private final String KEY_CUSTOMER_ID_CREATE = "customer:customerId";
    private final String KEY_CUSTOMER = "customer:%s";
    private final String KEY_CUSTOMER_IDS = "customer:customerIds";
    private final String KEY_CUSTOMER_LOGIN = "customer:login";

    @Override
    public Result<Customer> addCustomer(Customer customer) {
        try (Jedis jedis = JedisManager.getJedis()) {
            customer.setId(jedis.incr(KEY_CUSTOMER_ID_CREATE).intValue());
            customer.setPassword(DigestUtils.sha256Hex(customer.getMobileNo() + customer.getPassword()));
            String customerKey = String.format(KEY_CUSTOMER, customer.getId() + "");
            Transaction trans = jedis.multi();
            trans.hset(customerKey, "id", customer.getId() + "");
            trans.hset(customerKey, "email", StringUtils.isNotBlank(customer.getEmail()) ? customer.getEmail() : "");
            trans.hset(customerKey, "mobileNo", StringUtils.isNotBlank(customer.getMobileNo()) ? customer.getMobileNo() : "");
            trans.hset(customerKey, "password", StringUtils.isNotBlank(customer.getPassword()) ? customer.getPassword() : "");
            trans.hset(customerKey, "sex", customer.getSex() != null ? customer.getSex().name() : Customer.Sex.MALE.name());
            trans.hset(customerKey, "createTime", System.currentTimeMillis() + "");
            trans.hset(customerKey, "updateTime", System.currentTimeMillis() + "");
            trans.rpush(KEY_CUSTOMER_IDS, customer.getId() + "");
            trans.hset(KEY_CUSTOMER_LOGIN, customer.getPassword(), customer.getId() + "");
            trans.exec();
            return new Result<>(true, customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        }
    }

    @Override
    public Result<Customer> login(String mobileNo, String password) {
        try (Jedis jedis = JedisManager.getJedis()) {
            String customerId = jedis.hget(KEY_CUSTOMER_LOGIN, DigestUtils.sha256Hex(mobileNo + password));
            if (StringUtils.isBlank(customerId)) {
                return new Result<>(false, "手机号码或密码错误！");
            }
            String customerKey = String.format(KEY_CUSTOMER, customerId);
            Customer customer = new Customer();
            customer.setId(Integer.parseInt(jedis.hget(customerKey, "id")));
            customer.setEmail(jedis.hget(customerKey, "email"));
            customer.setMobileNo(jedis.hget(customerKey, "mobileNo"));
            customer.setPassword(jedis.hget(customerKey, "password"));
            customer.setSex(Customer.Sex.MALE.name().equals(jedis.hget(customerKey, "sex")) ? Customer.Sex.MALE : Customer.Sex.FEMALE);
            customer.setCreateTime(new Date(Long.parseLong(jedis.hget(customerKey, "createTime"))));
            customer.setUpdateTime(new Date(Long.parseLong(jedis.hget(customerKey, "updateTime"))));
            return new Result<>(true, customer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        }
    }

    @Override
    public Result<?> countByMobileNo(String mobileNo) {
        try (Jedis jedis = JedisManager.getJedis()) {
            return new Result<>(true, jedis.llen(KEY_CUSTOMER_IDS).intValue());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        }
    }
}
