package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditClient implements ActionListener {

    JButton be;
    JTextField t1;
    JTextField t2;
    JTextField t3;
    JTextField t4;
    JTextField t5;

    EditClient() {
        JFrame frame = new JFrame("Edit client");

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

        t3 = new JTextField("Address");
        t3.setFont(new Font("Consolas", Font.PLAIN, 20));
        t3.setPreferredSize(new Dimension(400, 30));

        t4 = new JTextField("Email");
        t4.setFont(new Font("Consolas", Font.PLAIN, 20));
        t4.setPreferredSize(new Dimension(400, 30));

        t5 = new JTextField("Age");
        t5.setFont(new Font("Consolas", Font.PLAIN, 20));
        t5.setPreferredSize(new Dimension(400, 30));

        JButton b = new JButton("Update");
        b.setFont(new Font("Consolas", Font.PLAIN, 15));
        b.setBackground(new Color(0xff6200));
        b.setForeground(Color.WHITE);
        b.setPreferredSize(new Dimension(100, 40));
        b.setBorder(BorderFactory.createLineBorder(Color.black));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();
                Client cl = new Client(Integer.parseInt(t1.getText()), t2.getText(), t3.getText(), t4.getText(), Integer.parseInt(t5.getText()));
                clientBLL.updateClientById(cl, cl.getId());
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(t1);
        frame.add(be);
        frame.add(t2);
        frame.add(t3);
        frame.add(t4);
        frame.add(t5);
        frame.add(b);
        frame.setSize(500, 320);
        frame.getContentPane().setBackground(new Color(0x742020));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == be){
            int id = Integer.parseInt(t1.getText());
            ClientBLL clientBLL = new ClientBLL();
            Client client = clientBLL.findClientById(id);
            t2.setText(client.getName());
            t3.setText(client.getAddress());
            t4.setText(client.getEmail());
            t5.setText(Integer.toString(client.getAge()));
        }
    }
}
