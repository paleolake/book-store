package book.store.service;

import book.store.common.JedisManager;
import book.store.common.Result;
import book.store.model.Category;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 使用list保存图书分类信息的ID，使用Hashes保存单条图书分类信息
 */
public class CategoryServiceRedisImpl implements CategoryService {
    private final Logger logger = LogManager.getLogger(getClass().getCanonicalName());
    private final String KEY_CATEGORY = "category:%s";
    private final String KEY_CATEGORY_IDS = "category:ids";

    @Override
    public Result<?> addCategory(Category category) {
        try (Jedis jedis = JedisManager.getJedis()) {
            Transaction trans = jedis.multi();
            String categoryKey = String.format(KEY_CATEGORY, category.getId() + "");
            trans.hset(categoryKey, "id", category.getId() + "");
            trans.hset(categoryKey, "parentId", category.getParentId() + "");
            trans.hset(categoryKey, "categoryName", category.getCategoryName());
            trans.hset(categoryKey, "createTime", category.getCreateTime().getTime() + "");
            trans.hset(categoryKey, "updateTime", category.getUpdateTime().getTime() + "");
            trans.rpush(KEY_CATEGORY_IDS, category.getId() + "");
            trans.exec();
            return new Result(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Result<>(false, "程序异常！");
        }
    }

    @Override
    public List<Category> queryCategories() {
        try (Jedis jedis = JedisManager.getJedis()) {
            List<String> categoryIds = jedis.lrange(KEY_CATEGORY_IDS, 0, -1);
            List<Category> categories = new ArrayList<>(categoryIds.size());
            for (String categoryId : categoryIds) {
                String categoryKey = String.format(KEY_CATEGORY, categoryId);
                String id = jedis.hget(categoryKey, "id");
                String parentId = jedis.hget(categoryKey, "parentId");
                String categoryName = jedis.hget(categoryKey, "categoryName");
                String createTime = jedis.hget(categoryKey, "createTime");
                String updateTime = jedis.hget(categoryKey, "updateTime");
                categories.add(new Category(Integer.parseInt(id), Integer.parseInt(parentId),
                        categoryName, new Date(Long.parseLong(createTime)), new Date(Long.parseLong(updateTime))));
            }
            return categories;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
