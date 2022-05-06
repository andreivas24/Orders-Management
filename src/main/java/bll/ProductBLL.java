package bll;

import bll.validators.EmailValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class ProductBLL {

    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        Product pr = productDAO.findById(id);
        if (pr == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return pr;
    }

    public Product findProductByName(String name) {
        Product pr = productDAO.findByName(name);
        if (pr == null) {
            throw new NoSuchElementException("The product with name =" + name + " was not found!");
        }
        return pr;
    }

    public void insertProduct(Product pd) {
        productDAO.insert(pd);
    }

    public List<Product> findAllProducts() {
        List<Product> list = productDAO.findAll();
        if (list == null) {
            throw new NoSuchElementException("There are no products");
        }
        return list;
    }

    public void updateProductById(Product pd, int id) {
        productDAO.update(pd, id);
    }

    public void deleteProduct(String name) {
        Product p = productDAO.findByName(name);
        productDAO.delete(p.getId());
    }

}
