package data.MySql;

import data.repos.UserRepository;
import model.Database.Address;
import model.Database.Cart;
import model.Database.User;
import model.Location;

import java.util.LinkedList;

public class UserMySQL implements UserRepository {

    @Override
    public LinkedList<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(int id) {

    }

    @Override
    public Address getUserAddress(int id) {
        return null;
    }

    @Override
    public LinkedList<User> getAllUsersByAddress(Location location) {
        return null;
    }

    @Override
    public Cart getShoppingCart(User user) {
        return null;
    }
}
