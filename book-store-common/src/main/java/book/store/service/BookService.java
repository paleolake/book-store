package book.store.service;

import book.store.model.Book;

import java.util.List;

public interface BookService {
    List<Book> queryBooks(Integer beginNum, Integer pageSize);

    Book findById(Integer bookId);

}
