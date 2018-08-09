package book.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class OrderInfo implements Serializable {
    private Integer id;
    private String orderCode;
    private Double orderAmt;
    private OrderState orderState;
    private Date createTime;
    private Date updateTime;
    private List<OrderDetail> orderDetails;


    public enum OrderState {NONPAID, PAID, CANCELLED}

    public OrderInfo() {
    }

    public OrderInfo(String orderCode, Double orderAmt, OrderState orderState, Date createTime, Date updateTime) {
        this.orderCode = orderCode;
        this.orderAmt = orderAmt;
        this.orderState = orderState;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Double getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(Double orderAmt) {
        this.orderAmt = orderAmt;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
