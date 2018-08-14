package book.store.service;

import book.store.common.Result;
import book.store.model.Category;

import java.util.List;

public interface CategoryService {

    Result<?> addCategory(Category category);

    List<Category> queryCategories();
}
