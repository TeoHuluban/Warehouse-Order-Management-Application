package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * This class represents the main panel that has three options for managing clients, products, or orders
 */
public class MainView extends JFrame {
    public MainView() {
        setTitle("Orders Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JButton clientsButton = new JButton("Clients");
        JButton productsButton = new JButton("Products");
        JButton ordersButton = new JButton("Orders");

        Dimension buttonSize = new Dimension(200, 50);
        clientsButton.setPreferredSize(buttonSize);
        productsButton.setPreferredSize(buttonSize);
        ordersButton.setPreferredSize(buttonSize);

        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientView clientView = new ClientView();
                clientView.setVisible(true);
            }
        });

        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductView productView = new ProductView();
                productView.setVisible(true);
            }
        });

        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderView orderView = new OrderView();
                orderView.setVisible(true);
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(clientsButton, gbc);

        gbc.gridy = 1;
        panel.add(productsButton, gbc);

        gbc.gridy = 2;
        panel.add(ordersButton, gbc);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        MainView mainFrame = new MainView();
        mainFrame.setVisible(true);
    }
}
