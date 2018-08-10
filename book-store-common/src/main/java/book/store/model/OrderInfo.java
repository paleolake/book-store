package book.store.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderInfo implements Serializable {
    private Integer id;
    private Integer customerId;
    private String orderCode;
    private Double orderAmt;
    private OrderState orderState;
    private Date createTime;
    private Date updateTime;
    private List<OrderDetail> orderDetails;


    public enum OrderState {
        NONPAID("未支付"),
        PAID("已支付"),
        CANCELLED("已取消");

        private final String label;

        OrderState(String label) {
            this.label = label;
        }

        public String label() {
            return this.label;
        }
    }

    public OrderInfo() {
    }

    public OrderInfo(Integer customerId, String orderCode, Double orderAmt, OrderState orderState, Date createTime, Date updateTime) {
        this.customerId = customerId;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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
        if (orderDetails == null) {
            orderDetails = new ArrayList<>();
        }
        return orderDetails;
    }

}
