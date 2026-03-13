package model.Database;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Cart {
    private int cartID;
    private User user;
    private LocalDateTime created_at;

    public Cart(int cartID, User user, LocalDateTime created_at) {
        this.cartID = cartID;
        this.user = user;
        this.created_at = created_at;
    }

    public int getCartID() {
        return cartID;
    }
    public User getUser() {
        return user;
    }
    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
