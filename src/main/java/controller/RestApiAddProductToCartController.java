package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import logic.SessionManager;
import model.Database.Cart;
import model.Database.CartItem;
import model.Database.Product;
import model.Database.User;
import model.ProductUserInput;
import model.Success;
import service.Repos;

import java.io.OutputStream;
import java.util.LinkedList;

import java.io.InputStream;

public class RestApiAddProductToCartController extends RestApiControllerAbstract {

    public RestApiAddProductToCartController(Repos repos) {
        super(repos);
    }

    @Override
    public void registerApi(HttpServer server) {
        server.createContext("/cart/add", this::apiCalledUpon);
    }

    @Override
    protected void apiCalledUpon(HttpExchange exchange) {
        logger.info("Received request to /api/cart/add");

        if (!ApiHelper.checkIFPost(exchange)) return;
        logger.info("Post request");

        InputStream in = exchange.getRequestBody();
        ObjectMapper mapper = new ObjectMapper();

        try {

            ProductUserInput productUserInput = mapper.readValue(in, ProductUserInput.class);
            logger.info("Product ID: " + productUserInput.product_id);

            if (!SessionManager.hasValidSession(exchange)) return;
            logger.info("Session is valid");

            User user = repos.getUserRepository().getUserByID(SessionManager.getUserIdFromSession(exchange));

            Product product = repos.getProductRepository().getProductByID(productUserInput.product_id);

            Cart cart =repos.getCartRepository().getCartByUser(user);

            LinkedList<CartItem> listCartItem= repos.getCartItemRepository().getCartItemByCartID(cart.getCartID());


            boolean happened = false;
            logger.info(happened + " " + listCartItem);

            if (listCartItem != null) {
                logger.info("Items found in the database");
                for (CartItem item : listCartItem) {
                    if (item.getProduct().getProductID() == product.getProductID()) {
                        item.increaseQuantity();
                        repos.getCartItemRepository().changeQuantity(item);
                        happened = true;
                    }
                }
            }else {
                logger.info("No items found in the database");
            }

            if (!happened){
                logger.info("Item not found in the database");
                logger.info("Adding item to the database"+product);
                repos.getCartItemRepository().addCartItem(new CartItem(0, cart, product, 1, product.getPrice()));
            }
            listCartItem= repos.getCartItemRepository().getCartItemByCartID(cart.getCartID());
            logger.info(listCartItem+" ");
            int quantity = 1;

            for (CartItem item :listCartItem){
                quantity += item.getQuantity();
            }

            String response = String.valueOf(quantity);
            logger.info("Response: " + response);

            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();


        }catch (Exception e){
            logger.severe("API response could not be sent: " + e.getMessage());
        }

    }
}
