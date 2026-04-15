package second.bootstrap;

import second.modell.Repos;
import second.controller.ControllerAllProducts;
import second.service.Config;
import second.service.PrepareConfig;
import second.view.Server;

public class Application {
    public static void main(String[] args) {
        Config config = PrepareConfig.getInstance().config;

        Repos repos = new Repos(config);

        new Server();

        new ControllerAllProducts(repos, "/products/all");

    }
}
