package book.store.model;

import java.io.Serializable;

public class CardDetail implements Serializable {
    private Book book;
    private Double amount;
    private Integer count;

    public CardDetail(Book book, Double amount, Integer count) {
        this.book = book;
        this.amount = amount;
        this.count = count;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
