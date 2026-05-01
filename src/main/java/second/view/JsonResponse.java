package second.view;

public class JsonResponse {
    public Object data;
    private int success;
    private String message;

    public JsonResponse(){}

    public JsonResponse(Object data, int success, String message){
        this.data = data;
        this.success = success;
        this.message = message;
    }
    public JsonResponse(String message, int success){
        this.message = message;
        this.success = success;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JsonResponse{");
        sb.append("data=").append(data);
        sb.append(", success=").append(success);
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getMessage() {
        return message;
    }
    public int getSuccess() {
        return success;
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
