package Model;

/**
 * This class models the product which has an id, name, stock, and price.
 */
public class Product {
    private int id;
    private String name;
    private int stock;
    private int price;

    public Product() {
    }

    public Product(String name, int stock, int price) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(int idProduct) {
        this.id = idProduct;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
