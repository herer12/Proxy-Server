    package model.Database;

    import java.util.Date;

    public class Product {
        private int productID;
        private String name;
        private String description;
        private double price;
        private Category category;
        private Date created_at;

        public Product(int productID, String name, String description, double price, Category category, Date created_at) {
            this.productID = productID;
            this.name = name;
            this.description = description;
            this.price = price;
            this.category = category;
            this.created_at = created_at;
        }

        public int getProductID() {
            return productID;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }

        public Category getCategory() {
            return category;
        }

        public Date getCreated_at() {
            return created_at;
        }

    }
