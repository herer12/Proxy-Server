package model.Database;

public class CartItem {
    private int cartItemID;
    private Cart cart;
    private Product product;
    private int quantity;
    private double price;

    public CartItem(int cartItemID, Cart cart, Product product, int quantity, double price) {
        this.cartItemID = cartItemID;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getCartItemID() {
        return cartItemID;
    }
    public Cart getCart() {
        return cart;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }

    public void increaseQuantity() {
        quantity++;
    }
    public void decreaseQuantity() {
        quantity--;
    }

    public Product getProduct() {
        return product;
    }
    public String getString(){
        return "CartItem: " + cartItemID + " " + product.getName() + " " + quantity;
    }
}
