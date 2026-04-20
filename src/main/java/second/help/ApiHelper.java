package second.help;

import at.herer12_erik_van_haentjens.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import second.view.JsonResponse;

import java.io.OutputStream;

public class ApiHelper {
    private static Logger logger = Logger.getLogger(ApiHelper.class);

    public static String prepareJson(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(object);
            return json;
        }catch (Exception e){
            logger.error("Error during preperation of json with the object "+object +" ",e.getMessage() );
            return null;
        }
    }

    public static void checkIfResponseIsOK(JsonResponse jsonResponse){
        if (jsonResponse.data == null){
            jsonResponse.setSuccess(0);
            jsonResponse.setMessage("Data is null");
        }
        if (jsonResponse.data == "{}"){
            jsonResponse.setSuccess(0);
            jsonResponse.setMessage("Data is not available");
        }
    }

    public static void sendResponse(HttpExchange exchange, JsonResponse response){
        String data = response.data.toString();

        try {
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, data.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(data.getBytes());
            os.close();
        } catch (Exception e) {
            logger.error("Problem with the deserialization with the data "+ response.data +". ", e.getMessage());
        }
    }


}
