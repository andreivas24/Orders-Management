package bll;

import bll.validators.StockValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Orders;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

public class OrderBLL {

    private OrderDAO orderDAO;
    private Validator validator;

    public OrderBLL(){
        orderDAO = new OrderDAO();
        validator = new StockValidator();
    }

    public void placeOrder(int idClient, int idProdus, int quantity){
        Orders order = new Orders(idClient,idProdus,quantity,0);
        validator.validate(order);
        ProductBLL productBLL = new ProductBLL();
        Product pr = productBLL.findProductById(idProdus);
        orderDAO.addOrder(idClient,idProdus,quantity,pr);
        productBLL.updateProductById(pr,idProdus);
        pr = productBLL.findProductById(idProdus);
        if(pr.getStock() == 0){
            productBLL.deleteProduct(pr.getName());
        }
    }

    public List<Orders> findAllOrders(){
        List<Orders> list = orderDAO.findAll();
        if(list == null){
            throw new NoSuchElementException("There are no orders");
        }
        return list;
    }

}