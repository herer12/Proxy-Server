package second.view;

public class JsonResponse {
    public Object data;
    private int success;
    private String message;

    public String getMessage() {
        return message;
    }
    public boolean hasSuccess() {
        return success==1;
    }
    public void setSuccess(int success) {
        this.success = success;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
