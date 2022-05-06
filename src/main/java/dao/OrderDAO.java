package dao;

import bll.ProductBLL;
import connection.ConnectionFactory;
import model.Orders;
import model.Product;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAO extends AbstractDAO<Orders> {

    private String createInsertOrder(ArrayList<String> fields){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO orders (");
        for(String str:fields){
            if(fields.get(0).equals(str)){
                continue;
            }
            if(fields.get(fields.size()-1).equals(str)){
                sb.append(str+") ");
                break;
            }
            sb.append(str+", ");
        }
        sb.append(" VALUES (");
        for(String str:fields){
            if(fields.get(0).equals(str)){
                continue;
            }
            if(fields.get(fields.size()-1).equals(str)){
                sb.append("?);");
                break;
            }
            sb.append("?,");
        }
        return sb.toString();
    }

    public void addOrder(int idCl, int idprod, int quantity, Product pr){
        Connection connection = null;
        PreparedStatement statement = null;
        Orders o = new Orders();
        Class<?> c = o.getClass();
        ArrayList<String> fields = new ArrayList<>();
        for(Field f:c.getDeclaredFields()){
            fields.add(f.getName());
        }
        String query = createInsertOrder(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            double price = pr.getPrice()*quantity;
            int stockLeft = pr.getStock() - quantity;
            pr.setStock(stockLeft);
            statement.setInt(1,idCl);
            statement.setInt(2,idprod);
            statement.setInt(3,quantity);
            statement.setDouble(4,price);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
    }

}

