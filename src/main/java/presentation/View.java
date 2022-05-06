package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame {

    public View(){

        GridLayout layout = new GridLayout(5,1,20,20);
        this.setLayout(layout);

        JLabel label = new JLabel("Hello, humble human!");
        label.setFont(new Font("Calibri",Font.BOLD,18));
        label.setForeground(Color.WHITE);
        label.setSize(200,20);

        JButton b1 = new JButton("Process clients");
        b1.setBackground(new Color(0x2e7eff));
        b1.setForeground(Color.WHITE);
        b1.setFont(new Font("Calibri",Font.BOLD,20));
        b1.setSize(80,200);
        b1.setBorder(BorderFactory.createLineBorder(Color.black));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b1){
                    new ControllerClients();
                }
            }
        });

        JButton b2 = new JButton("Process products");
        b2.setBackground(new Color(0x2e7eff));
        b2.setForeground(Color.WHITE);
        b2.setFont(new Font("Calibri",Font.BOLD,20));
        b2.setSize(80,200);
        b2.setBorder(BorderFactory.createLineBorder(Color.black));
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b2){
                    new ControllerProducts();
                }
            }
        });

        JButton b3 = new JButton("Place order");
        b3.setBackground(new Color(0x2e7eff));
        b3.setForeground(Color.WHITE);
        b3.setFont(new Font("Calibri",Font.BOLD,20));
        b3.setSize(80,200);
        b3.setBorder(BorderFactory.createLineBorder(Color.black));
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b3){
                    new ControllerOrder();
                }
            }
        });

        JButton b4 = new JButton("View orders");
        b4.setBackground(new Color(0x2e7eff));
        b4.setForeground(Color.WHITE);
        b4.setFont(new Font("Calibri",Font.BOLD,20));
        b4.setSize(80,200);
        b4.setBorder(BorderFactory.createLineBorder(Color.black));
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == b4){
                    new ViewOrders();
                }
            }
        });

        this.add(label);
        this.add(b1);
        this.add(b2);
        this.add(b3);
        this.add(b4);
        this.setSize(300,500);
        this.setTitle("View Tables");
        this.getContentPane().setBackground(new Color(0x03b800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

}
