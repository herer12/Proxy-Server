package second.bootstrap;

import second.controller.Controller;
import second.controller.ControllerAllProducts;
import second.repository.Database_Type;
import second.repository.mySQL.MySqlProductsDAO;
import second.repository.general.ProductsDAO;
import second.help.Config;
import second.help.PrepareConfig;
import second.service.GetAllProductsService;
import second.view.ApiAllProducts;
import second.view.Server;

public class Application {
    private static ProductsDAO repo;

        public static void main(String[] args) {

            // Load Config
            Config config = PrepareConfig.getInstance().config;

            // DB Auswahl
            createProductsDAO(config.getDatabase_type());

            // Service
            GetAllProductsService service = new GetAllProductsService(repo);

            // Controller
            Controller controller = new ControllerAllProducts(service);

            // API / Server
            Server.getInstance();

            new ApiAllProducts("/products/all", controller);
        }


    private static ProductsDAO createProductsDAO(Database_Type type) {
        switch (type) {
            case MYSQL:
                return new MySqlProductsDAO();

            default:
                throw new RuntimeException("Unsupported DB type");
        }
    }
}