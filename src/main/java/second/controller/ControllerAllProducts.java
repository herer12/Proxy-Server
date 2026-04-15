package second.controller;

import second.modell.Repos;
import second.view.ApiAllProducts;

public class ControllerAllProducts extends Controller {
    public ControllerAllProducts(Repos repos, String path) {
        super(repos, path);
        restApi = new ApiAllProducts(path, this);
    }
}
