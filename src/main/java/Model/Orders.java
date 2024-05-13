package Model;

/**
 * This class models the order which has an id, idClient, idProduct, and quantity.
 */
public class Orders {
    private int id;
    private int idClient;
    private int idProduct;
    private int quantity;

    public Orders() {
    }

    public Orders(int idClient, int idProduct, int quantity) {
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int idOrder) {
        this.id = idOrder;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
