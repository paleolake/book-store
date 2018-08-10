package book.store.model;

import java.io.Serializable;
import java.util.Date;


public class OrderDetail implements Serializable {
    private Integer id;
    private Integer orderId;
    private Integer bookId;
    private Double bookAmt;
    private Integer bookCount;
    private Date updateTime;

    private String bookName;

    public OrderDetail() {
    }

    public OrderDetail(Integer bookId, Double bookAmt, Integer bookCount, Date updateTime) {
        this.bookId = bookId;
        this.bookAmt = bookAmt;
        this.bookCount = bookCount;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Double getBookAmt() {
        return bookAmt;
    }

    public void setBookAmt(Double bookAmt) {
        this.bookAmt = bookAmt;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
