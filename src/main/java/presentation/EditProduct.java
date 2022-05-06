package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProduct implements ActionListener {

    JButton be;
    JTextField t1;
    JTextField t2;
    JTextField t3;
    JTextField t4;

    EditProduct() {
        JFrame frame = new JFrame("Edit product");

        t1 = new JTextField("id");
        t1.setFont(new Font("Consolas", Font.PLAIN, 20));
        t1.setPreferredSize(new Dimension(400, 30));

        be = new JButton("Edit");
        be.setFont(new Font("Consolas", Font.PLAIN, 15));
        be.setBackground(new Color(0xff6200));
        be.setForeground(Color.WHITE);
        be.setPreferredSize(new Dimension(100, 40));
        be.setBorder(BorderFactory.createLineBorder(Color.black));
        be.addActionListener(this);


        t2 = new JTextField("Name");
        t2.setFont(new Font("Consolas", Font.PLAIN, 20));
        t2.setPreferredSize(new Dimension(400, 30));

        t3 = new JTextField("Stock");
        t3.setFont(new Font("Consolas", Font.PLAIN, 20));
        t3.setPreferredSize(new Dimension(400, 30));

        t4 = new JTextField("Price");
        t4.setFont(new Font("Consolas", Font.PLAIN, 20));
        t4.setPreferredSize(new Dimension(400, 30));

        JButton b = new JButton("Update");
        b.setFont(new Font("Consolas", Font.PLAIN, 15));
        b.setBackground(new Color(0xff6200));
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(100, 40));
        b.setBorder(BorderFactory.createLineBorder(Color.black));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductBLL productBLL = new ProductBLL();
                Product pd = new Product(Integer.parseInt(t1.getText()), t2.getText(), Integer.parseInt(t3.getText()), Double.parseDouble(t4.getText()));
                productBLL.updateProductById(pd,Integer.parseInt(t1.getText()));
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(t1);
        frame.add(be);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);
        frame.add(b);
        frame.setSize(500, 320);
        frame.getContentPane().setBackground(new Color(0xff6200));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == be){
            int id = Integer.parseInt(t1.getText());
            ProductBLL productBLL = new ProductBLL();
            Product pd = productBLL.findProductById(id);
            t2.setText(pd.getName());
            t3.setText(Integer.toString(pd.getStock()));
            t4.setText(Double.toString(pd.getPrice()));
        }
    }
}
