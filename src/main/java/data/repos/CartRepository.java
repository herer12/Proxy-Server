package data.repos;

import model.Database.Cart;
import model.Database.User;

public interface CartRepository {
    public void addCart(Cart cart);
    public void updateCart(Cart cart);
    public void deleteCart(Cart cart);
    public Cart getCartByUser(User user);


}
