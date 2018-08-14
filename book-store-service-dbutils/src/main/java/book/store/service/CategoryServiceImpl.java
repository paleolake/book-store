package book.store.service;

import book.store.common.Result;
import book.store.model.Category;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class CategoryServiceImpl extends BaseService implements CategoryService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());

    @Override
    public Result<Category> addCategory(Category category) {
        try {
            category.setParentId((category.getParentId() == null || category.getParentId() < 0) ? 0 : category.getParentId());
            category.setCreateTime(new Date());
            category.setUpdateTime(new Date());
            String sql = "INSERT INTO category(parent_id,category_name,create_time,update_time)VALUES(?,?,NOW(),NOW());";
            Long categoryId = runner.insert(sql, new ScalarHandler<>(), new Object[]{category.getParentId(), category.getCategoryName()});
            category.setId(categoryId.intValue());
            return new Result<>(true, category);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result(false, "程序异常！");
        }

    }

    @Override
    public List<Category> queryCategories() {
        try {
            return runner.query("SELECT * FROM category", new BeanListHandler<>(Category.class, rowProcessor));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
