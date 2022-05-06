package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.Client;
import model.Orders;
import start.ReflectionExample;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ViewOrders{

    ViewOrders(){
        OrderBLL orderBLL = new OrderBLL();
        JFrame frame = new JFrame("View orders");
        List<Orders> orders = orderBLL.findAllOrders();
        List<String> fieldsList = ReflectionExample.getFields(orders.get(0));
        String[] fields = new String[fieldsList.size()];
        int i = 0;
        for(String field:fieldsList){
            fields[i++] = field;
        }

        Object[][] data = new Object[orders.size()][fieldsList.size()];
        i=0;
        for(Orders order:orders){
            ArrayList<Object> obj = ReflectionExample.getValues(order);
            int j=0;
            for(Object o : obj){
                data[i][j++] = o;
            }
            i++;
        }

        JTable table = new JTable(data, fields);
        table.setPreferredSize(new Dimension(600,400));

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        frame.add(scrollPane);

        frame.setLayout(new FlowLayout());
        frame.setSize(700, 500);
        frame.getContentPane().setBackground(new Color(0x742020));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }



}
