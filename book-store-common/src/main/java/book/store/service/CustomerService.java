package book.store.service;

import book.store.common.Result;
import book.store.model.Customer;

public interface CustomerService {

    Result<Customer> addCustomer(Customer customer);

    Result<Customer> login(String mobileNo, String password);

    Result<?> countByMobileNo(String mobileNo);
}
