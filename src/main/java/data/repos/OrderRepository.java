package data.repos;

import model.Database.Order;
import model.Database.User;

import java.util.LinkedList;

public interface OrderRepository {
    public void addOrder(Order order);
    public void updateOrder(Order order);
    public void deleteOrder(Order order);
    public Order getOrderByID(Order order);
    public LinkedList<Order> getOrdersByUser(User user);
    public LinkedList<Order> getAllOrders();
}
