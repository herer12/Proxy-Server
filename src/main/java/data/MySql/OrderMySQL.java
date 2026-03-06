package data.MySql;

import data.repos.OrderRepository;
import model.Database.Order;
import model.Database.User;

import java.util.LinkedList;

public class OrderMySQL implements OrderRepository {
    @Override
    public void addOrder(Order order) {

    }

    @Override
    public void updateOrder(Order order) {

    }

    @Override
    public void deleteOrder(Order order) {

    }

    @Override
    public Order getOrderByID(Order order) {
        return null;
    }

    @Override
    public LinkedList<Order> getOrdersByUser(User user) {
        return null;
    }

    @Override
    public LinkedList<Order> getAllOrders() {
        return null;
    }
}
