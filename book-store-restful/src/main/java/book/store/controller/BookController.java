package book.store.controller;

import book.store.common.Result;
import book.store.model.Book;
import book.store.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController extends BaseController {
    private final Logger logger = LogManager.getLogger(getClass().getName());

    @RequestMapping({"/books", "/books/"})
    public Result<?> queryBooks(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) String categoryId) {
        try {
            int beginNum = (pageNo - 1) * pageSize;
            List<Book> books = serviceFactory.createService(BookService.class).queryBooks(beginNum, pageSize, categoryId);
            return new Result(true, books);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }


    @RequestMapping(value = "/book/{bookId}")
    public Result<?> findById(@PathVariable("bookId") Integer bookId) {
        try {
            Book book = serviceFactory.createService(BookService.class).findById(bookId);
            if (book == null) {
                return new Result(false, "图书信息不存在！");
            }
            return new Result(true, book);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }
    }
}
