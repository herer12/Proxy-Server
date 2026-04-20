package second.objects;

import java.sql.Timestamp;

public class Product {
    private final int product_id;
    private String name;
    private String description;
    private double price;
    private int category_id;
    private Timestamp created_at;

    Product(int product_id){
        this.product_id = product_id;
    }

    public Product(int product_id, String name, String description, double price, int category_id, Timestamp created_at){
        this.product_id = product_id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category_id = category_id;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product{");
        sb.append("product_id=").append(product_id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", price=").append(price);
        sb.append(", category_id=").append(category_id);
        sb.append(", created_at=").append(created_at);
        sb.append('}');
        return sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getProduct_id() {
        return product_id;
    }
}
