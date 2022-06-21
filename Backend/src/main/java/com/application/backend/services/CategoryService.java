package com.application.backend.services;

import com.application.backend.entities.Category;
import com.application.backend.repository.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {
    public CategoryService() {}

    @Inject
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return this.categoryRepository.addCategory(category);
    }

    public List<Category> allCategory() {
        return this.categoryRepository.allCategory();
    }

    public Category findCategory(String name) {
        return this.categoryRepository.findCategory(name);
    }

    public void deleteCategory(String name) {
        this.categoryRepository.deleteCategory(name);
    }

    public Category updateCategory(Category category, String name) { return this.categoryRepository.updateCategory(category, name); }
}
