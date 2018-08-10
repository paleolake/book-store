package book.store.servlet;

import book.store.common.Constants;
import book.store.model.Book;
import book.store.model.CardDetail;
import book.store.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "card", value = {"/card.htm", "/card/addBook.htm", "/card/buy.htm"})
public class CartServlet extends BaseHttpServlet {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            //加入购物车和立即购买
            if (StringUtils.contains(req.getRequestURI(), "/card/addBook") || StringUtils.contains(req.getRequestURI(), "/card/buy.htm")) {
                String bookId = req.getParameter("bookId");
                String price = req.getParameter("price");
                if (StringUtils.isBlank(bookId) || StringUtils.isBlank(price)) {
                    req.setAttribute("error", "参数非法！");
                    req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
                    return;
                }

                Book book = serviceFactory.createService(BookService.class).findById(Integer.parseInt(bookId));
                if (book == null || book.getPrice() < Double.parseDouble(price)) {
                    req.setAttribute("error", "参数非法！");
                    req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
                    return;
                }
                Map<String, CardDetail> details = (Map<String, CardDetail>) req.getSession(true).getAttribute(Constants.CARD_DETAIL);
                if (details == null) {
                    details = new LinkedHashMap<>();
                    req.getSession().setAttribute(Constants.CARD_DETAIL, details);
                }
                CardDetail cardDetail = details.get(bookId);
                if (cardDetail != null) {
                    cardDetail.setAmount(Double.parseDouble(price));
                    cardDetail.setCount(cardDetail.getCount() + 1);
                } else {
                    details.put(bookId, new CardDetail(book, Double.parseDouble(price), 1));
                }
                req.setAttribute("cardDetails", details.values());
                if (StringUtils.contains(req.getRequestURI(), "/card/addBook")) {
                    req.getRequestDispatcher("/WEB-INF/views/jsp/trade/card.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(String.format("%s/account/buy.htm?bookId=%s", req.getContextPath(), bookId));
                }
                return;
            }

            Map<String, CardDetail> details = (Map<String, CardDetail>) req.getSession(true).getAttribute(Constants.CARD_DETAIL);
            if (details != null) {
                req.setAttribute("cardDetails", details.values());
            }
            req.getRequestDispatcher("/WEB-INF/views/jsp/trade/card.jsp").forward(req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("error", "程序异常！");
            req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
        }
    }

}
