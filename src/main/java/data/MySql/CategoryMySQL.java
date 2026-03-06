package data.MySql;

import data.repos.CategoryRepository;
import model.Database.Category;

import java.util.LinkedList;

public class CategoryMySQL implements CategoryRepository {
    @Override
    public void addCategory(String name, String description) {

    }

    @Override
    public void updateCategory(String name, String description) {

    }

    @Override
    public void deleteCategory(int id) {

    }

    @Override
    public LinkedList<Category> getAllCategories() {
        return null;
    }

    @Override
    public LinkedList<Category> getAllCategoriesByName(String name) {
        return null;
    }
}
