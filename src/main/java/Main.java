import BusinessLogic.ClientBLL;
import Connection.ConnectionFactory;
import DataAccess.ClientDAO;
import Model.Client;

import javax.swing.*;
import java.sql.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        ClientBLL cb = new ClientBLL();
        Client c = new Client("aasd", "asda", "asda", "asdsa");
        Client c1 = cb.findClientById(3);
        //c1.setName("Diana");
        //cb.updateClient(c1);
        try{
        System.out.println(cb.findClientById(1));
        } catch(Exception e){
            JOptionPane.showMessageDialog(null, "Nu exista!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }
}
