package Presentation;

import BusinessLogic.ClientBLL;
import Model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
/**
 * This class represents the client-managing panel that has four options: add new client, edit client, delete client,
 * view all clients in a table
 */
public class ClientView extends JFrame {
    int buttonPressed;
    public ClientView() {
        setTitle("Client Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 400);
        setLocationRelativeTo(null);

        JButton addClientButton = new JButton("Add New Client");
        JButton editClientButton = new JButton("Edit Client");
        JButton deleteClientButton = new JButton("Delete Client");
        JButton viewAllClientsButton = new JButton("View All Clients");
        JButton backButton = new JButton("Back");
        JButton confirmButton = new JButton("Confirm");
        Dimension buttonSize = new Dimension(130, 30);
        addClientButton.setPreferredSize(buttonSize);
        editClientButton.setPreferredSize(buttonSize);
        deleteClientButton.setPreferredSize(buttonSize);
        viewAllClientsButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(new Dimension(90, 30));
        confirmButton.setPreferredSize(new Dimension(90, 30));

        Panel panel0 = new Panel();
        Panel panel1 = new Panel();
        Panel panel2 = new Panel();
        Panel panel3 = new Panel();
        Panel panel4 = new Panel();
        panel0.add(addClientButton);
        panel1.add(editClientButton);
        panel2.add(deleteClientButton);
        panel3.add(viewAllClientsButton);
        panel4.add(backButton);

        JLabel label0 = new JLabel("Id: ");
        JLabel label1 = new JLabel("Name: ");
        JLabel label2 = new JLabel("Email: ");
        JLabel label3 = new JLabel("Phone: ");
        JLabel label4 = new JLabel("Address: ");
        JTextField field0 = new JTextField(35);
        JTextField field1 = new JTextField(35);
        JTextField field2 = new JTextField(35);
        JTextField field3 = new JTextField(35);
        JTextField field4 = new JTextField(35);
        Panel panel5 = new Panel();
        Panel panel6 = new Panel();
        Panel panel7 = new Panel();
        Panel panel8 = new Panel();
        Panel panel9 = new Panel();
        Panel panel10 = new Panel();
        panel5.add(label0);
        panel5.add(field0);
        panel6.add(label1);
        panel6.add(field1);
        panel7.add(label2);
        panel7.add(field2);
        panel8.add(label3);
        panel8.add(field3);
        panel9.add(label4);
        panel9.add(field4);
        panel10.add(confirmButton);

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
        topPanel.add(panel9);
        bottomPanel.add(panel10);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);

        panel5.setVisible(false);
        panel6.setVisible(false);
        panel7.setVisible(false);
        panel8.setVisible(false);
        panel9.setVisible(false);
        panel10.setVisible(false);

        ClientBLL bll = new ClientBLL();

        addClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel5.setVisible(false);
                panel6.setVisible(true);
                panel7.setVisible(true);
                panel8.setVisible(true);
                panel9.setVisible(true);
                panel10.setVisible(true);
                mainPanel.revalidate();

                buttonPressed = 0;
            }
        });

        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel5.setVisible(true);
                panel6.setVisible(true);
                panel7.setVisible(true);
                panel8.setVisible(true);
                panel9.setVisible(true);
                panel10.setVisible(true);
                mainPanel.revalidate();

                buttonPressed = 1;
            }
        });

        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel5.setVisible(true);
                panel6.setVisible(false);
                panel7.setVisible(false);
                panel8.setVisible(false);
                panel9.setVisible(false);
                panel10.setVisible(true);
                mainPanel.revalidate();

                buttonPressed = 2;
            }
        });

        viewAllClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    TableGenerator<Client> generator = new TableGenerator<>(Client.class);
                    List<Client> clients = bll.findAllClients();

                    JTable table = new JTable();
                    generator.generateTable(clients, table);

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
                Client c = new Client(field1.getText(), field2.getText(), field3.getText(), field4.getText());

                if(buttonPressed == 0) {
                    bll.insertClient(c);
                    JOptionPane.showMessageDialog(null, "New client added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

                if(buttonPressed == 1) {
                    try{
                        int id = Integer.parseInt(field0.getText());
                        c.setId(id);
                        bll.updateClient(c);
                        JOptionPane.showMessageDialog(null, "Client updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if(buttonPressed == 2) {
                    try {
                        int id = Integer.parseInt(field0.getText());
                        bll.deleteClientById(id);
                        JOptionPane.showMessageDialog(null, "Client deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        ClientView clientView = new ClientView();
        clientView.setVisible(true);
    }
}
