package BusinessLogic;

import DataAccess.ClientDAO;
import Model.Client;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class manages and calls the methods from the data access class
 */
public class ClientBLL {
    private final ClientDAO clientDAO;

    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    public List<Client> findAllClients() {
        try {
            return clientDAO.findAll();
        }
        catch(Exception e) {
            throw new NoSuchElementException("No clients were found!");
        }
    }

    public Client findClientById(int id) {
        try {
            return (Client) clientDAO.findById(id);
        } catch(Exception e) {
            throw new NoSuchElementException("The client with id = " + id + " was not found!");
        }
    }

    public void deleteClientById(int id) {
        findClientById(id);
        clientDAO.deleteById(id);
    }

    public void insertClient(Client c) {
        clientDAO.insert(c);
    }

    public void updateClient(Client c) {
        findClientById(c.getId());
        clientDAO.update(c);
    }
}
