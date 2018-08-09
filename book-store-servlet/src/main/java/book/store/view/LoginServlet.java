package book.store.view;

import book.store.common.Constants;
import book.store.common.Result;
import book.store.model.Customer;
import book.store.service.CustomerService;
import book.store.util.CheckUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", value = "/login.htm")
public class LoginServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String mobileNo = req.getParameter("mobileNo");
            if (StringUtils.isBlank(mobileNo) || !CheckUtils.checkMobileNo(mobileNo)) {
                req.setAttribute("error", "手机号码不合法！");
                req.getRequestDispatcher("/WEB-INF/views/jsp/account/login.jsp").forward(req, resp);
                return;
            }
            String password = req.getParameter("password");
            if (StringUtils.isBlank(password) || !CheckUtils.checkPsw(password)) {
                req.setAttribute("error", "登录密码不合法！");
                req.getRequestDispatcher("/WEB-INF/views/jsp/account/login.jsp").forward(req, resp);
                return;
            }

            Result<Customer> result = CustomerService.getInstance().login(mobileNo, password);
            if (!result.isSuccess()) {
                req.setAttribute("error", result.getError());
                req.getRequestDispatcher("/WEB-INF/views/jsp/account/login.jsp").forward(req, resp);
                return;
            }
            req.getSession(true).setAttribute(Constants.SESSION_CUSTOMER, result.getModel());
            req.getRequestDispatcher("/WEB-INF/views/jsp/common/success.jsp").forward(req, resp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            req.setAttribute("error", "程序异常！");
            req.getRequestDispatcher("/WEB-INF/views/jsp/account/register.jsp").forward(req, resp);
        }
    }
}
