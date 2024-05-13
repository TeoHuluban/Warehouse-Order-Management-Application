package Presentation;

import BusinessLogic.BillBLL;
import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import BusinessLogic.ProductBLL;
import Model.Bill;
import Model.Client;
import Model.Orders;
import Model.Product;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class represents the order-managing panel where the user can choose from the tables an existing client
 * and product, and a quantity for that product to make an order
 */
public class OrderView extends JFrame {
    public OrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        ClientBLL bll1 = new ClientBLL();
        TableGenerator<Client> generator1 = new TableGenerator<>(Client.class);
        List<Client> clients = bll1.findAllClients();
        JTable table1 = new JTable();
        generator1.generateTable(clients, table1);

        ProductBLL bll2 = new ProductBLL();
        TableGenerator<Product> generator2 = new TableGenerator<>(Product.class);
        List<Product> products = bll2.findAllProducts();
        JTable table2 = new JTable();
        generator2.generateTable(products, table2);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JTextField f1 = new JTextField(20);
        topPanel.add(new JLabel("Id client: "));
        topPanel.add(f1);

        JTextField f2 = new JTextField(20);
        topPanel.add(new JLabel("Id product: "));
        topPanel.add(f2);

        JTextField f3 = new JTextField(20);
        topPanel.add(new JLabel("Quantity: "));
        topPanel.add(f3);

        JButton confirmButton = new JButton("Confirm");
        topPanel.add(confirmButton);

        JButton backButton = new JButton("Back");
        topPanel.add(backButton);

        JPanel bottomPanel = new JPanel();
        JScrollPane scrollPanel1 = new JScrollPane(table1);
        JScrollPane scrollPanel2 = new JScrollPane(table2);
        bottomPanel.add(scrollPanel1);
        bottomPanel.add(scrollPanel2);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderBLL bll = new OrderBLL();
                try {
                    bll1.findClientById(Integer.parseInt(f1.getText()));              //throws exception if the id was not found
                    Product p = bll2.findProductById(Integer.parseInt(f2.getText())); //throws exception if the id was not found

                    if(Integer.parseInt(f3.getText()) < 0)
                        throw new NoSuchElementException("Negative quantity!");

                    if(Integer.parseInt(f3.getText()) > p.getStock())
                        throw new NoSuchElementException("Not enough stock!("+p.getStock()+")");

                    p.setStock(p.getStock() - Integer.parseInt(f3.getText())); //calculate new stock
                    bll2.updateProduct(p); //update stock

                    Orders o = new Orders(Integer.parseInt(f1.getText()), Integer.parseInt(f2.getText()), Integer.parseInt(f3.getText())); //create new order
                    bll.insertOrder(o); //insert new order
                    (new BillBLL()).insertBill(new Bill(0, LocalDateTime.now(), o.getId(), o.getQuantity() * p.getPrice())); //create and insert new bill
                    JOptionPane.showMessageDialog(null, "Order added!\nBill generated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch(NoSuchElementException ee){
                    JOptionPane.showMessageDialog(null, ee.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Empty field!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        OrderView orderView = new OrderView();
        orderView.setVisible(true);
    }
}
