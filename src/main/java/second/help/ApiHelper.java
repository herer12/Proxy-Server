package second.help;

import at.herer12_erik_van_haentjens.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import second.objects.Account;
import second.view.JsonResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.stream.Collectors;

public class ApiHelper {
    private static Logger logger = Logger.getLogger(ApiHelper.class);

    public static String prepareJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            logger.error("Error during preparation of json: ", e.getMessage());
            return null;
        }
    }

    public static void checkIfResponseIsOK(JsonResponse jsonResponse) {
        if (jsonResponse.data == null) {
            jsonResponse.setSuccess(0);
            jsonResponse.setMessage("Data is null");
        } else if (jsonResponse.data instanceof java.util.Collection<?> list && list.isEmpty()) {
            jsonResponse.setSuccess(0);
            jsonResponse.setMessage("No data available");
        } else {
            jsonResponse.setSuccess(1);
        }
    }

    public static void sendResponse(HttpExchange exchange, JsonResponse response) {
        try {
            String json = prepareJson(response);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        } catch (Exception e) {
            logger.error("Problem with sending response: ", e.getMessage());
        }
    }

    private static final ObjectMapper mapper = new ObjectMapper();


    public static Account deserializeRegister(HttpExchange exchange)  {
        try {
            Account account = deserializeLogin(exchange);

            account.setCreated_at(new Timestamp(System.currentTimeMillis()));
            account.setIs_active(1);

            return account;
        }catch (Exception e){
            logger.error("Error during deserialization of register", e.getMessage());
            return null;
        }
    }


    public static Account deserializeLogin(HttpExchange exchange)  {
        try {
            String json = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                    .lines().collect(Collectors.joining());

            Account account = mapper.readValue(json, Account.class);

            return account;
        }catch (Exception e){
            logger.error("Error during deserialization of login", e.getMessage());
            return null;
        }
    }
}
