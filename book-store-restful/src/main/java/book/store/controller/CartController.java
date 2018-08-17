package book.store.controller;

import book.store.common.Constants;
import book.store.common.Result;
import book.store.model.Book;
import book.store.model.CardDetail;
import book.store.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class CartController extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @GetMapping({"/card"})
    public Result<?> showCartDetail(HttpSession session) {
        return new Result<>(true, session.getAttribute(Constants.CARD_DETAIL));
    }

    @PostMapping({"/card"})
    public Result<?> addBook(Integer bookId, Double price, HttpSession session) {
        try {
            Book book = serviceFactory.createService(BookService.class).findById(bookId);
            if (book == null || book.getPrice() < price) {
                return new Result<>(false, "参数非法！");
            }
            Map<Integer, CardDetail> details = (Map<Integer, CardDetail>) session.getAttribute(Constants.CARD_DETAIL);
            if (details == null) {
                details = new LinkedHashMap<>();
                session.setAttribute(Constants.CARD_DETAIL, details);
            }
            CardDetail cardDetail = details.get(bookId);
            if (cardDetail != null) {
                cardDetail.setAmount(price);
                cardDetail.setCount(cardDetail.getCount() + 1);
            } else {
                details.put(bookId, new CardDetail(book, price, 1));
            }
            return new Result<>(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }
}
