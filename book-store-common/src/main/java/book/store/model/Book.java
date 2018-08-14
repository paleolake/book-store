package book.store.model;

import java.io.Serializable;
import java.util.Date;


public class Book implements Serializable {
    private Integer id;
    private Integer categoryId;
    private String bookName;
    private String author;
    private String publisherName;
    private Double price;
    private Date publishDate;
    private Date createTime;
    private Date updateTime;

    public Book() {
    }

    public Book(Integer categoryId, String bookName, String author, String publisherName, Double price, Date publishDate) {
        this.categoryId = categoryId;
        this.bookName = bookName;
        this.author = author;
        this.publisherName = publisherName;
        this.price = price;
        this.publishDate = publishDate;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", price=" + price +
                ", publishDate=" + publishDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
