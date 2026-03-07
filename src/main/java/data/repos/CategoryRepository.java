package data.repos;

import model.Database.Category;

import java.util.LinkedList;

public interface CategoryRepository {
    public void addCategory(String name, String description);
    public void updateCategory(String name, String description);
    public void deleteCategory(int id);
    public LinkedList<Category> getAllCategories();
    public LinkedList<Category> getAllCategoriesByName(String name);
    public Category getCategoryByID(int id);
}
