package second.controller;

import second.objects.Account;
import second.service.LoginService;
import second.view.JsonResponse;

public class ControllerLogin extends Controller{
    private LoginService service;

    public ControllerLogin(LoginService service) {
        this.service = service;
    }

    public JsonResponse login(Account account){
        logger.info("Login");
        String message= "";
        int success = 0;
        if(service.execute(account)){
            success = 1;
        }
        logger.info("Login success: "+success);
        logger.info("Login message: "+message);
        return new JsonResponse(message,success);
    }
}
