package second.controller;

import second.objects.Account;
import second.service.RegisterService;
import second.view.JsonResponse;

public class ControllerRegister extends Controller{
    private RegisterService service;
    public ControllerRegister(RegisterService service){
        this.service = service;
    }
    public JsonResponse register(Account account){
        int success;
        String message;
        Object data;
        if (!checkPasswordStructure(account)) return new JsonResponse(301, 0, "Password Structure isn't right");
        if (!checkEmailStructure(account)) return new JsonResponse(302, 0, "Email Structure isn't right");
        if (!checkUsernameStructure(account)) return new JsonResponse(303, 0, "Username Structure isn't right");
        if (!checKPhoneNumberStructure(account)) return new JsonResponse(304, 0, "Phone Number Structure isn't right");
        if (checkIfAccountExist(account)) return new JsonResponse(500, 0, "Account already exist");

        if (service.createAccount(account)){
            success = 1;
            message = "Account created";
            data = 200;
        }else{
            success = 0;
            message = "Account not created";
            data = 501;
        }
        return new JsonResponse(data, success,message);

    }

    private boolean checkPasswordStructure(Account account){
        if (account.getPassword().length() < 8) return false;
        return true;
    }

    private boolean checkEmailStructure(Account account){
        return true;
    }
    private boolean checkUsernameStructure(Account account){
        return true;
    }
    private boolean checkIfAccountExist(Account account){
        return service.checkIfAccountExists(account);
    }
    private boolean checKPhoneNumberStructure(Account account){
        return true;
    }
}
