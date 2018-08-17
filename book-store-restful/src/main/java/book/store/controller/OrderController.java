package book.store.controller;

import book.store.common.Constants;
import book.store.common.Num;
import book.store.common.Result;
import book.store.model.CardDetail;
import book.store.model.Customer;
import book.store.model.OrderDetail;
import book.store.model.OrderInfo;
import book.store.service.OrderService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @GetMapping({"/orders", "/orders/"})
    public Result<?> queryOrders(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session) {
        try {
            Customer customer = (Customer) session.getAttribute(Constants.SESSION_CUSTOMER);
            if (customer == null) {
                return new Result(false, "会话已失效！");
            }
            int beginNum = (pageNo - 1) * pageSize;
            List<OrderInfo> orders = serviceFactory.createService(OrderService.class).queryOrders(customer.getId(), beginNum, pageSize, null);
            return new Result<>(true, orders);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }

    @PostMapping({"/order"})
    public Result<?> createOrder(HttpServletRequest req, HttpSession session) {
        try {
            Customer customer = (Customer) session.getAttribute(Constants.SESSION_CUSTOMER);
            if (customer == null) {
                return new Result(false, "会话已失效！");
            }
            String[] bookIds = req.getParameterValues("bookId");
            if (bookIds == null || bookIds.length <= 0) {
                return new Result<>(false, "参数非法！");
            }
            String orderCode = String.format("%s%s", DateFormatUtils.format(new Date(), "yyMMddHHmmssSSS"), RandomUtils.nextInt(10000, 99999));
            OrderInfo orderInfo = new OrderInfo(customer.getId(), orderCode, 0d, OrderInfo.OrderState.NONPAID, new Date(), new Date());
            for (String bookId : bookIds) {
                Map<String, CardDetail> details = (Map<String, CardDetail>) session.getAttribute(Constants.CARD_DETAIL);
                CardDetail cardDetail = details.remove(bookId);
                if (StringUtils.isBlank(bookId) || cardDetail == null) {
                    return new Result<>(false, "参数非法！");
                }
                OrderDetail orderDetail = new OrderDetail(cardDetail.getBook().getId(), cardDetail.getAmount(), cardDetail.getCount(), new Date());
                orderInfo.getOrderDetails().add(orderDetail);
                orderInfo.setOrderAmt(Num.create(cardDetail.getAmount()).mul(cardDetail.getCount()).add(orderInfo.getOrderAmt()).doubleValue());
            }
            Result<?> result = serviceFactory.createService(OrderService.class).addOrder(orderInfo);
            if (!result.isSuccess()) {
                return new Result<>(false, result.getError());
            }
            return new Result<>(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }
}
