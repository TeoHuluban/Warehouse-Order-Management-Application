package Presentation;

import BusinessLogic.ProductBLL;
import Model.Client;
import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class represents the product-managing panel that has four options: add new product, edit product, delete product,
 * view all products in a table
 */
public class ProductView extends JFrame {
    int buttonPressed;

    public ProductView() {
        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);

        JButton addProductButton = new JButton("Add New Product");
        JButton editProductButton = new JButton("Edit Product");
        JButton deleteProductButton = new JButton("Delete Product");
        JButton viewAllProductsButton = new JButton("View All Products");
        JButton backButton = new JButton("Back");
        JButton confirmButton = new JButton("Confirm");
        Dimension buttonSize = new Dimension(150, 30);
        addProductButton.setPreferredSize(buttonSize);
        editProductButton.setPreferredSize(buttonSize);
        deleteProductButton.setPreferredSize(buttonSize);
        viewAllProductsButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(new Dimension(90, 30));
        confirmButton.setPreferredSize(new Dimension(90, 30));

        Panel panel0 = new Panel();
        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        Panel panel3 = new Panel();
        Panel panel4 = new Panel();
        panel0.add(addProductButton);
        panel1.add(editProductButton);
        panel2.add(deleteProductButton);
        panel3.add(viewAllProductsButton);
        panel4.add(backButton);

        JLabel label0 = new JLabel("Id: ");
        JLabel label1 = new JLabel("Name: ");
        JLabel label2 = new JLabel("Stock: ");
        JLabel label3 = new JLabel("Price: ");
        JTextField field0 = new JTextField(35);
        JTextField field1 = new JTextField(35);
        JTextField field2 = new JTextField(35);
        JTextField field3 = new JTextField(35);
        Panel panel5 = new Panel();
        Panel panel6 = new Panel();
        Panel panel7 = new Panel();
        Panel panel8 = new Panel();
        Panel panel9 = new Panel();
        panel5.add(label0);
        panel5.add(field0);
        panel6.add(label1);
        panel6.add(field1);
        panel7.add(label2);
        panel7.add(field2);
        panel8.add(label3);
        panel8.add(field3);
        panel9.add(confirmButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        topPanel.add(panel0);
        topPanel.add(panel1);
        topPanel.add(panel2);
        topPanel.add(panel3);
        topPanel.add(panel4);
        topPanel.add(panel5);
        topPanel.add(panel6);
        topPanel.add(panel7);
        topPanel.add(panel8);
        bottomPanel.add(panel9);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        panel5.setVisible(false);
        panel6.setVisible(false);
        panel7.setVisible(false);
        panel8.setVisible(false);
        panel9.setVisible(false);

        ProductBLL bll = new ProductBLL();

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel5.setVisible(false);
                panel6.setVisible(true);
                panel7.setVisible(true);
                panel8.setVisible(true);
                panel9.setVisible(true);
                mainPanel.revalidate();

                buttonPressed = 0;
            }
        });

        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel5.setVisible(true);
                panel6.setVisible(true);
                panel7.setVisible(true);
                panel8.setVisible(true);
                panel9.setVisible(true);
                mainPanel.revalidate();

                buttonPressed = 1;
            }
        });

        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel5.setVisible(true);
                panel6.setVisible(false);
                panel7.setVisible(false);
                panel8.setVisible(false);
                panel9.setVisible(true);
                mainPanel.revalidate();

                buttonPressed = 2;
            }
        });

        viewAllProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableGenerator<Product> generator = new TableGenerator<>(Product.class);
                List<Product> products = bll.findAllProducts();

                JTable table = new JTable();
                generator.generateTable(products, table);

                JFrame frame = new JFrame("Clients");
                frame.setLocationRelativeTo(null);
                frame.add(new JScrollPane(table));
                frame.setSize(600, 400);
                frame.setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonPressed == 0) {
                    try {
                        if(Integer.parseInt(field2.getText()) < 0)
                            throw new NoSuchElementException("Negative quantity!");
                        if(Integer.parseInt(field3.getText()) < 0)
                            throw new NoSuchElementException("Negative price!");
                        Product p = new Product(field1.getText(), Integer.parseInt(field2.getText()), Integer.parseInt(field3.getText()));
                        bll.insertProduct(p);
                        JOptionPane.showMessageDialog(null, "New product added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch(NoSuchElementException ia){
                        JOptionPane.showMessageDialog(null, ia.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Empty field!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (buttonPressed == 1) {
                    try {
                        if(Integer.parseInt(field2.getText()) < 0)
                            throw new NoSuchElementException("Negative quantity!");
                        if(Integer.parseInt(field3.getText()) < 0)
                            throw new NoSuchElementException("Negative price!");
                        Product p = new Product(field1.getText(), Integer.parseInt(field2.getText()), Integer.parseInt(field3.getText()));
                        int id = Integer.parseInt(field0.getText());
                        p.setId(id);
                        bll.updateProduct(p);
                        JOptionPane.showMessageDialog(null, "Product updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch(NoSuchElementException ia){
                        JOptionPane.showMessageDialog(null, ia.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Empty field!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if (buttonPressed == 2) {
                    try {
                        int id = Integer.parseInt(field0.getText());
                        bll.deleteProductById(id);
                        JOptionPane.showMessageDialog(null, "Product deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch(NoSuchElementException ia){
                        JOptionPane.showMessageDialog(null, ia.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Empty field!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        ProductView productView = new ProductView();
        productView.setVisible(true);
    }
}
