package book.store.servlet;

import book.store.model.Book;
import book.store.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "book", value = "/book/detail.htm")
public class BookServlet extends BaseHttpServlet {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String bookId = req.getParameter("bookId");
            Book book = serviceFactory.createService(BookService.class).findById(Integer.parseInt(bookId));
            if (StringUtils.isBlank(bookId) || book == null) {
                req.setAttribute("error", "参数非法！");
                req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("book", book);
            req.getRequestDispatcher("/WEB-INF/views/jsp/book/detail.jsp").forward(req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("error", "程序异常！");
            req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
        }
    }
}
