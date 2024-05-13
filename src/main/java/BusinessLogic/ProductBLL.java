package BusinessLogic;

import DataAccess.OrderDAO;
import DataAccess.ProductDAO;
import Model.Product;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * This class manages and calls the methods from the data access class
 */
public class ProductBLL {
    private final ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public List<Product> findAllProducts() {
        try {
            return productDAO.findAll();
        }
        catch(Exception e) {
            throw new NoSuchElementException("No products were found!");
        }
    }

    public Product findProductById(int id) {
        try {
            return (Product) productDAO.findById(id);
        } catch(Exception e) {
            throw new NoSuchElementException("The product with id = " + id + " was not found!");
        }
    }

    public void deleteProductById(int id) {
        findProductById(id);
        productDAO.deleteById(id);
    }

    public void insertProduct(Product c) {
        productDAO.insert(c);
    }

    public void updateProduct(Product c) {
        findProductById(c.getId());
        productDAO.update(c);
    }
}
