package book.store.controller;

import book.store.service.DefaultServiceFactory;
import book.store.service.ServiceFactory;

public class BaseController {
    protected ServiceFactory serviceFactory = DefaultServiceFactory.getInstance();
}
