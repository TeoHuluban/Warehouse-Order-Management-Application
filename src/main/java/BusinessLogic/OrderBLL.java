package BusinessLogic;

import DataAccess.OrderDAO;
import Model.Orders;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class manages and calls the methods from the data access class
 */
public class OrderBLL {
    private final OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    public List<Orders> findAllOrders() {
        try {
            return orderDAO.findAll();
        }
        catch(Exception e) {
            throw new NoSuchElementException("No orders were found!");
        }
    }

    public Orders findOrderById(int id) {
        try {
            return (Orders) orderDAO.findById(id);
        } catch(Exception e) {
            throw new NoSuchElementException("The order with id = " + id + " was not found!");
        }
    }

    public void deleteOrderById(int id) {
        findOrderById(id);
        orderDAO.deleteById(id);
    }

    public void insertOrder(Orders c) {
        orderDAO.insert(c);
    }

    public void updateOrder(Orders c) {
        findOrderById(c.getId());
        orderDAO.update(c);
    }
}
