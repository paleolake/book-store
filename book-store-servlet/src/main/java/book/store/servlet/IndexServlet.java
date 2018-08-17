package book.store.servlet;

import book.store.service.BookService;
import book.store.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "index", value = "/index.htm")
public class IndexServlet extends BaseHttpServlet {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("books", serviceFactory.createService(BookService.class).queryBooks(0, 10, null));
            req.getRequestDispatcher("/WEB-INF/views/jsp/index.jsp").forward(req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            req.getRequestDispatcher("/WEB-INF/views/jsp/common/error.jsp").forward(req, resp);
        }
    }
}
