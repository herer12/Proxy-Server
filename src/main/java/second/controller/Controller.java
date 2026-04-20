package second.controller;

import at.herer12_erik_van_haentjens.logging.Logger;
import second.view.RestApi;

public abstract class Controller {
    protected Logger logger = Logger.getLogger(Controller.class);
    protected RestApi restApi;


    public Controller(){
    }
}
