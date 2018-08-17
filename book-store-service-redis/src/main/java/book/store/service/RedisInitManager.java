package book.store.service;

import book.store.common.JedisManager;
import book.store.common.Result;
import book.store.model.Book;
import book.store.model.Category;
import book.store.service.BookService;
import book.store.service.CategoryService;
import book.store.service.DefaultServiceFactory;
import book.store.service.SystemService;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Date;
import java.util.List;


public class RedisInitManager implements SystemService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public void init() {
        try {
            File inputFile = new File("book-store-service-redis/src/main/resources/data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //清空redis中所有键值
            JedisManager.getJedis().flushAll();

            //图书分类
            NodeList nList = doc.getElementsByTagName("category");
            CategoryService categoryService = DefaultServiceFactory.getInstance().createService(CategoryService.class);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    System.out.println("categoryName : " + element.getElementsByTagName("categoryName").item(0).getTextContent());
                    Integer id = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                    Integer parentId = Integer.parseInt(element.getElementsByTagName("parentId").item(0).getTextContent());
                    String categoryName = element.getElementsByTagName("categoryName").item(0).getTextContent();
                    Result<?> result = categoryService.addCategory(new Category(id, parentId, categoryName, new Date(), new Date()));
                    if (!result.isSuccess()) {
                        System.out.println(String.format("新增图书类别[%s]失败！", categoryName));
                    }
                }
            }

            //图书信息
            nList = doc.getElementsByTagName("book");
            BookService bookService = DefaultServiceFactory.getInstance().createService(BookService.class);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nNode;
                    System.out.println("bookName : " + element.getElementsByTagName("bookName").item(0).getTextContent());
                    Book book = new Book();
                    book.setId(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()));
                    book.setCategoryId(Integer.parseInt(element.getElementsByTagName("categoryId").item(0).getTextContent()));
                    book.setBookName(element.getElementsByTagName("bookName").item(0).getTextContent());
                    book.setAuthor(element.getElementsByTagName("author").item(0).getTextContent());
                    book.setPublisherName(element.getElementsByTagName("publisherName").item(0).getTextContent());
                    book.setPrice(Double.parseDouble(element.getElementsByTagName("price").item(0).getTextContent()));
                    book.setPublishDate(DateUtils.parseDate(element.getElementsByTagName("publishDate").item(0).getTextContent(), "yyyy/MM/dd"));
                    book.setCreateTime(new Date());
                    book.setUpdateTime(new Date());
                    Result<?> result = bookService.addBook(book);
                    if (!result.isSuccess()) {
                        System.out.println(String.format("新增图书信息[%s]失败！", book.getBookName()));
                    }
                }
            }

            //查询数据用来校验数据是否已初始化
            List<Category> categories = categoryService.queryCategories();
            logger.info(categories);
            List<Book> books = bookService.queryBooks(0, 100);
            logger.info(books);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
