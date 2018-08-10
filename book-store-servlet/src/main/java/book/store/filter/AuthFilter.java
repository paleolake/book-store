package book.store.filter;

import book.store.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "auth", value = "/account/*")
public class AuthFilter implements Filter {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (StringUtils.contains(req.getRequestURI(), "/account/") && (session == null || session.getAttribute(Constants.SESSION_CUSTOMER) == null)) {
            logger.warn("Unauthorized access request!");
            res.sendRedirect(req.getContextPath() + "/login.htm");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
