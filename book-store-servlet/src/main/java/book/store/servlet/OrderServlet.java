package book.store.servlet;

import book.store.common.Constants;
import book.store.common.Num;
import book.store.common.Result;
import book.store.model.*;
import book.store.service.BookService;
import book.store.service.CustomerService;
import book.store.service.OrderService;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet(name = "order", value = {"/account/order.htm", "/account/buy.htm"})
public class OrderServlet extends BaseHttpServlet {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (StringUtils.contains(req.getRequestURI(), "/account/buy.htm")) {
                String[] bookIds = req.getParameterValues("bookId");
                if (bookIds == null || bookIds.length <= 0) {
                    req.setAttribute("error", "参数非法！");
                    req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
                    return;
                }
                String orderCode = String.format("%s%s", DateFormatUtils.format(new Date(), "yyMMddHHmmssSSS"), RandomUtils.nextInt(10000, 99999));
                Customer customer = (Customer) req.getSession(true).getAttribute(Constants.SESSION_CUSTOMER);
                OrderInfo orderInfo = new OrderInfo(customer.getId(), orderCode, 0d, OrderInfo.OrderState.NONPAID, new Date(), new Date());
                for (String bookId : bookIds) {
                    Map<String, CardDetail> details = (Map<String, CardDetail>) req.getSession(true).getAttribute(Constants.CARD_DETAIL);
                    CardDetail cardDetail = details.remove(bookId);
                    if (StringUtils.isBlank(bookId) || cardDetail == null) {
                        req.setAttribute("error", "参数非法！");
                        req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
                        return;
                    }
                    OrderDetail orderDetail = new OrderDetail(cardDetail.getBook().getId(), cardDetail.getAmount(), cardDetail.getCount(), new Date());
                    orderInfo.getOrderDetails().add(orderDetail);
                    orderInfo.setOrderAmt(Num.create(cardDetail.getAmount()).mul(cardDetail.getCount()).add(orderInfo.getOrderAmt()).doubleValue());
                }
                Result<?> result = serviceFactory.createService(OrderService.class).addOrder(orderInfo);
                if (!result.isSuccess()) {
                    req.setAttribute("error", result.getError());
                    req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
                    return;
                }
            }

            String pageNo = req.getParameter("pageNo");
            String pageSize = req.getParameter("pageSize");
            pageNo = (pageNo != null ? pageNo : "1");
            pageSize = (pageSize != null ? pageSize : "10");
            int beginIndex = (Integer.parseInt(pageNo) - 1) * Integer.parseInt(pageSize);
            Customer customer = (Customer) req.getSession(true).getAttribute(Constants.SESSION_CUSTOMER);
            List<OrderInfo> orders = serviceFactory.createService(OrderService.class).queryOrders(customer.getId(), beginIndex, Integer.parseInt(pageSize), null);
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/WEB-INF/views/jsp/trade/order.jsp").forward(req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("error", "程序异常！");
            req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
        }
    }
}
