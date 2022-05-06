package bll.validators;

import bll.ProductBLL;
import model.Orders;
import model.Product;

public class StockValidator  implements Validator<Orders> {

    @Override
    public void validate(Orders order) {
        ProductBLL productBLL = new ProductBLL();
        Product product = productBLL.findProductById(order.getIdproduct());
        if (order.getQuantity() < 1 || order.getQuantity() > product.getStock()) {
            throw new IllegalArgumentException("The Product Stock limit is not respected!");
        }
    }
}
