package book.store.view;

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

@WebServlet(name = "card", value = {"/card.htm", "/card/addBook.htm"})
public class CartServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (StringUtils.contains(req.getRequestURI(), "/card/addBook")) {
                String bookId = req.getParameter("bookId");
                String price = req.getParameter("price");
                if (StringUtils.isBlank(bookId) || StringUtils.isBlank(price)) {
                    resp.getWriter().write("false:参数非法！");
                    resp.getWriter().flush();
                    return;
                }

                Book book = BookService.getInstance().findById(Integer.parseInt(bookId));
                if (book == null || book.getPrice() < Double.parseDouble(price)) {
                    resp.getWriter().write("false:参数非法！");
                    resp.getWriter().flush();
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
                resp.getWriter().write("true:成功加入购物车！");
                resp.getWriter().flush();
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
