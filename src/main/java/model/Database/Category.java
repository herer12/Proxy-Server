package model.Database;

public class Category {
    private final int categoryID;
    private String name;
    private String description;

    public Category(int categoryID,String name, String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.description = description;
    }
    public int getCategoryID() {
        return categoryID;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
