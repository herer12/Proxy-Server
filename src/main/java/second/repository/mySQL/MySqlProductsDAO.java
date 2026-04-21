package second.repository.mySQL;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.objects.Product;
import second.repository.MySqlConnection;
import second.repository.general.ProductsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.LinkedList;

public class MySqlProductsDAO implements ProductsDAO {

    Logger logger = Logger.getLogger(MySqlProductsDAO.class);
    String sqlQueryAllProducts = "SELECT product_id, name, description, price, category_id, created_at, image_path FROM product";

    private Product buildProduct(ResultSet rs){
        try {
            int product_id = rs.getInt("product_id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            double price = rs.getDouble("price");
            int category_id = rs.getInt("category_id");
            Timestamp created_at = rs.getTimestamp("created_at");
            String image_path = rs.getString("image_path");

            return new Product(product_id, name, description, price, category_id, created_at, image_path);


        } catch (Exception e) {
            logger.error("Error building product", e);
            return null;
        }
    }

    private LinkedList<Product> buildProducts(ResultSet rs){
        LinkedList<Product> products = new LinkedList<>();

        try {
            while (rs.next()){
                products.add(buildProduct(rs));
            }
        }catch (Exception e){
            logger.error("Error building products", e);
        }

        return products;
    }

    @Override
    public LinkedList<Product> getAllProducts() {
        logger.debug("Getting all products");
        LinkedList<Product> products = new LinkedList<>();

        try (Connection conn = MySqlConnection.getInstance().getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sqlQueryAllProducts);
            ResultSet rs = stmt.executeQuery();
            products = buildProducts(rs);

        } catch (Exception e) {
            logger.error("Error getting all products", e);
        }

        return products;
    }


}
