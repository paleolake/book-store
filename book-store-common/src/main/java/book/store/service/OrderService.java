package book.store.service;

import book.store.common.Result;
import book.store.model.OrderInfo;

import java.util.List;

public interface OrderService {

    Result<?> addOrder(OrderInfo orderInfo);

    List<OrderInfo> queryOrders(Integer beginIndex, Integer pageSize);

}
