package data.repos;

import model.Database.CartItem;

import java.util.LinkedList;

public interface CartItemRepository {
    public void addCartItem(CartItem cartItem);
    public LinkedList<CartItem> getCartItemByCartID(int id);
    public void changeQuantity(CartItem cartItem);

}
