package data.repos;

import model.Database.Address;
import model.Database.Cart;
import model.Database.User;
import model.Location;

import java.util.LinkedList;

public interface UserRepository {
    public LinkedList<User> getAllUsers();
    public User getUserByEmail(String email);
    public User getUserByID(int id);
    public boolean addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public Address getUserAddress(int id);
    public LinkedList<User> getAllUsersByAddress(Location location);
    public Cart getShoppingCart(User user);
}
