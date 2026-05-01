package second.bootstrap;

import second.controller.*;
import second.repository.Database_Type;
import second.repository.general.AccountDAO;
import second.repository.mySQL.MySqlAccountDAO;
import second.repository.mySQL.MySqlProductsDAO;
import second.repository.general.ProductsDAO;
import second.help.Config;
import second.help.PrepareConfig;
import second.service.GetAllProductsService;
import second.service.LoginService;
import second.service.RegisterService;
import second.service.SearchProductsService;
import second.view.*;

public class Application {
    private static ProductsDAO productsDAORepo;
    private static AccountDAO accountRepo;

        public static void main(String[] args) {

            // Load Config
            Config config = PrepareConfig.getInstance().config;

            // DB Auswahl
            createProductsDAO(config.getDatabase_type());

            // Service
            GetAllProductsService serviceAllProducts = new GetAllProductsService(productsDAORepo);
            SearchProductsService serviceSearchProducts = new SearchProductsService(productsDAORepo);
            LoginService serviceLogin = new LoginService(accountRepo);
            RegisterService serviceRegister = new RegisterService(accountRepo);

            // Controller
            Controller controllerAllProducts = new ControllerAllProducts(serviceAllProducts);
            Controller controllerSearchProducts = new ControllerSearchProducts(serviceSearchProducts);
            Controller controllerLogin = new ControllerLogin(serviceLogin);
            Controller controllerRegister = new ControllerRegister(serviceRegister);
            Controller controllerLoggedIn = new ControllerLoggedIn();

            // API / Server
            Server.getInstance();

            new ApiAllProducts("/products/all", controllerAllProducts);
            new ApiSearchProducts("/products/search", controllerSearchProducts);
            new ApiLogin("/auth/login", controllerLogin);
            new ApiRegister("/auth/register", controllerRegister);
            new ApiLoggedIn("/auth/loggedin", controllerLoggedIn);
        }


    private static void createProductsDAO(Database_Type type) {
        switch (type) {
            case MYSQL:
                productsDAORepo = new MySqlProductsDAO();
                accountRepo = new MySqlAccountDAO();
                break;
            default:
                throw new RuntimeException("Unsupported DB type");
        }
    }
}