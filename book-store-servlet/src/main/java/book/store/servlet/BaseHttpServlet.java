package book.store.servlet;

import book.store.service.DefaultServiceFactory;
import book.store.service.ServiceFactory;

import javax.servlet.http.HttpServlet;

public class BaseHttpServlet extends HttpServlet {
    protected ServiceFactory serviceFactory = DefaultServiceFactory.getInstance();

}
