package book.store.controller;

import book.store.common.Constants;
import book.store.common.Result;
import book.store.model.CardDetail;
import book.store.model.Customer;
import book.store.service.CustomerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
public class CustomerController extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @PostMapping("/login")
    public Result<?> login(String mobileNo, String password, HttpServletRequest req, HttpSession session) {
        try {
            Result<Customer> result = serviceFactory.createService(CustomerService.class).login(mobileNo, password);
            if (result.isSuccess()) {
                Map<Integer, CardDetail> details = (Map<Integer, CardDetail>) session.getAttribute(Constants.CARD_DETAIL);
                session.invalidate();//作废登录前的会话
                session = req.getSession(true);
                session.setAttribute(Constants.SESSION_CUSTOMER, result.getModel());
                session.setAttribute(Constants.CARD_DETAIL, details);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }

    @PostMapping("/register")
    public Result<?> register(String mobileNo, String password, HttpServletRequest req, HttpSession session) {
        try {
            Result<?> result = serviceFactory.createService(CustomerService.class).countByMobileNo(mobileNo);
            if (!result.isSuccess()) {
                return new Result(false, "手机号码已注册！");
            }

            Customer customer = new Customer();
            customer.setMobileNo(mobileNo);
            customer.setPassword(password);
            result = serviceFactory.createService(CustomerService.class).addCustomer(customer);
            if (result.isSuccess()) {
                Map<Integer, CardDetail> details = (Map<Integer, CardDetail>) session.getAttribute(Constants.CARD_DETAIL);
                session.invalidate();//作废登录前的会话
                session = req.getSession(true);
                session.setAttribute(Constants.SESSION_CUSTOMER, result.getModel());
                session.setAttribute(Constants.CARD_DETAIL, details);
            }
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }

    @GetMapping("/logout")
    public Result<?> logout(HttpSession session) {
        session.invalidate();
        return new Result<>(true);
    }
}
