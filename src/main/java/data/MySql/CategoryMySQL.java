package data.MySql;

import data.repos.CategoryRepository;
import model.Database.Category;
import service.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;

public class CategoryMySQL implements CategoryRepository {

    private DatabaseManagerMySQL dbManager;
    Logger logger = Logger.getInstance();


    public CategoryMySQL(DatabaseManagerMySQL dbManager) {
        this.dbManager = dbManager;
    }

    String getAllCategoriesQuery = "SELECT category_id, name, description FROM category";
    String getCategoryByIDQuery = getAllCategoriesQuery+" WHERE category_id = ?";

    private Category mapCategory(ResultSet resultSet){
        try {
            int category_id = resultSet.getInt("category_id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            return new Category(category_id, name, description);
        }catch (Exception e){
            logger.severe("Could not map category: " + e.getMessage());
            return null;
        }

    }

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

    @Override
    public Category getCategoryByID(int id) {
        logger.info("Getting category by ID: " + id);
        try (Connection connection = dbManager.mysqlConnection()){
            PreparedStatement statement = connection.prepareStatement(getCategoryByIDQuery);
            statement.setInt(1, id);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()) {          // ← WICHTIG
                return mapCategory(resultSet);
            }

            logger.warning("Category not found: " + id);
            return null;

        }catch (Exception e){
            logger.severe("Could not connect to MySQL: " + e.getMessage());
            return null;
        }
    }
}
