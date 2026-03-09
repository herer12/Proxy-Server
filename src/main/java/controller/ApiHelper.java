    package controller;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.sun.net.httpserver.HttpExchange;
    import service.Logger;

    import java.io.IOException;
    import java.io.OutputStream;
    import java.util.LinkedList;

    public class ApiHelper {
        private static Logger logger=Logger.getInstance();

        public static <T>  void sendingJson(HttpExchange exchange, LinkedList<T> list )  {
            try {
            logger.info("API called upon");

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(list);

            logger.info("JSON: " + json);

            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, json.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes());
            os.close();
            logger.info("API response sent");

        } catch (IOException e) {
            logger.severe("API response could not be sent: " + e.getMessage());
        }}
        public static boolean checkIFPost(HttpExchange exchange){
            if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                logger.severe("HTTP method not supported");
                try {

                    exchange.sendResponseHeaders(405, -1);
                    exchange.getResponseBody().close();

                }catch (IOException e){
                    logger.severe("API response could not be sent: " + e.getMessage());

                }
                return false;
            }
            return true;
        }
    }
