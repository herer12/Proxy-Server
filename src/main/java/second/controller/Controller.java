package second.controller;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.modell.Repos;
import second.view.RestApi;

public abstract class Controller {
    protected Logger logger = Logger.getLogger(Controller.class);
    protected RestApi restApi;
    protected Repos repos;

    public Controller(Repos repos, String path){
        this.repos = repos;
    }
}
