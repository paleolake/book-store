package book.store.service;

import book.store.common.Result;
import book.store.model.Book;

import java.util.List;

public interface BookService {
    List<Book> queryBooks(Integer beginNum, Integer pageSize, Object... params);

    Book findById(Integer bookId);

    Result<?> addBook(Book book);

}
