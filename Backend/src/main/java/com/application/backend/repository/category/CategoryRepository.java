package com.application.backend.repository.category;

import com.application.backend.entities.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> allCategory();
    Category addCategory(Category category);
    Category updateCategory(Category category, String name);
    Category findCategory(String name);
    void deleteCategory(String name);
}
